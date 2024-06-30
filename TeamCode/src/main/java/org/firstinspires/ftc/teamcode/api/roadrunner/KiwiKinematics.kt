package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.Arclength
import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Pose2dDual
import com.acmerobotics.roadrunner.PosePath
import com.acmerobotics.roadrunner.PoseVelocity2dDual
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.VelConstraint
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.Polar2dDual
import kotlin.math.PI
import kotlin.math.abs

data class KiwiKinematics(val radius: Double) {
    data class WheelIncrements<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    )

    // Variant of `TriWheels.inverse()` for dual numbers.
    fun <Param> forward(w: WheelIncrements<Param>): Twist2dDual<Param> {
        // Convert increments to cartesian coordinates.
        val r = Polar2dDual(DualNum.constant(TriWheels.RED_ANGLE - PI_2, 2), w.red).toCartesian()
        val g = Polar2dDual(DualNum.constant(TriWheels.GREEN_ANGLE - PI_2, 2), w.green).toCartesian()
        val b = Polar2dDual(DualNum.constant(TriWheels.BLUE_ANGLE - PI_2, 2), w.blue).toCartesian()

        return Twist2dDual(
            // Sum cartesian coordinates, then divide by coefficient of 1.5.
            (r + g + b) / 1.5,
            // Sum of all increments define rotation.
            w.red + w.green + w.blue,
        )
    }

    data class WheelVelocities<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    ) {
        fun all() = listOf(red, green, blue)
    }

    // Variant of `TriWheels.compute()` for dual numbers.
    fun <Param> inverse(t: PoseVelocity2dDual<Param>): WheelVelocities<Param> {
        val (theta, radius) = Polar2dDual.fromCartesian(t.linearVel.x, t.linearVel.y)

        return WheelVelocities(
            radius * (theta.unaryMinus() + TriWheels.RED_ANGLE).sin() + t.angVel,
            radius * (theta.unaryMinus() + TriWheels.GREEN_ANGLE).sin() + t.angVel,
            radius * (theta.unaryMinus() + TriWheels.BLUE_ANGLE).sin() + t.angVel,
        )
    }

    inner class WheelVelConstraint(val maxWheelVel: Double) : VelConstraint {
        override fun maxRobotVel(
            robotPose: Pose2dDual<Arclength>,
            path: PosePath,
            s: Double,
        ): Double {
            val txRobotWorld = robotPose.value().inverse()
            val robotVelWorld = robotPose.velocity().value()
            val robotVelRobot = txRobotWorld * robotVelWorld
            return inverse(PoseVelocity2dDual.constant<Arclength>(robotVelRobot, 1))
                .all()
                .minOf { abs(maxWheelVel / it.value()) }
        }
    }

    companion object {
        private const val PI_2: Double = PI / 2.0
    }
}
