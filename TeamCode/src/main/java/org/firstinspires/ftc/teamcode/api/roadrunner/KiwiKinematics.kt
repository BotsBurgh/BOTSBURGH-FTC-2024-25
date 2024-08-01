package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.Arclength
import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Pose2dDual
import com.acmerobotics.roadrunner.PosePath
import com.acmerobotics.roadrunner.PoseVelocity2dDual
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.VelConstraint
import org.firstinspires.ftc.teamcode.api.TriWheels.BLUE_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.GREEN_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.RED_ANGLE
import org.firstinspires.ftc.teamcode.utils.PI_2
import org.firstinspires.ftc.teamcode.utils.Polar2dDual
import kotlin.math.abs

class KiwiKinematics(val radius: Double) {
    data class WheelTicks<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    )

    fun <Param> forward(w: WheelTicks<Param>): Twist2dDual<Param> {
        // TODO: Find n property of kinematics dual numbers.
        val r = Polar2dDual(DualNum.constant(RED_ANGLE - PI_2, 4), w.red).toCartesian()
        val g = Polar2dDual(DualNum.constant(GREEN_ANGLE - PI_2, 4), w.green).toCartesian()
        val b = Polar2dDual(DualNum.constant(BLUE_ANGLE - PI_2, 4), w.blue).toCartesian()

        return Twist2dDual(
            (r + g + b) / 1.5,
            (w.red + w.green + w.blue) / radius,
        )
    }

    data class WheelVelocities<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    ) {
        fun all() = listOf(this.red, this.green, this.blue)
    }

    fun <Param> inverse(t: PoseVelocity2dDual<Param>): WheelVelocities<Param> {
        val polar = Polar2dDual.fromCartesian(t.linearVel)

        val redAngle = DualNum.constant<Param>(RED_ANGLE, polar.theta.size())
        val greenAngle = DualNum.constant<Param>(GREEN_ANGLE, polar.theta.size())
        val blueAngle = DualNum.constant<Param>(BLUE_ANGLE, polar.theta.size())

        return WheelVelocities(
            polar.radius * (redAngle - polar.theta) + t.angVel,
            polar.radius * (greenAngle - polar.theta) + t.angVel,
            polar.radius * (blueAngle - polar.theta) + t.angVel,
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

            return this@KiwiKinematics.inverse(
                PoseVelocity2dDual.constant<Arclength>(robotVelRobot, 1),
            ).all().minOf { abs(maxWheelVel / it.value()) }
        }
    }
}
