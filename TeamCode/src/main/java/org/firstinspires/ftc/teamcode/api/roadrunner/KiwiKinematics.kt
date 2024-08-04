package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.Arclength
import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.MecanumKinematics
import com.acmerobotics.roadrunner.Pose2dDual
import com.acmerobotics.roadrunner.PosePath
import com.acmerobotics.roadrunner.PoseVelocity2dDual
import com.acmerobotics.roadrunner.TankKinematics
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.VelConstraint
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.TriWheels.BLUE_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.GREEN_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.RED_ANGLE
import org.firstinspires.ftc.teamcode.utils.PI_2
import org.firstinspires.ftc.teamcode.utils.Polar2dDual
import kotlin.math.abs

/**
 * Used to compute the various physics related to the kiwi drive (Killough platform).
 *
 * This was based off of [MecanumKinematics] and [TankKinematics].
 */
class KiwiKinematics(private val radius: Double) {
    /**
     * Represents the amount of ticks each wheel on the drive rotated.
     */
    data class WheelTicks<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    )

    /**
     * Calculates the position and rotation of the robot based off of the wheel ticks.
     */
    fun <Param> forward(w: WheelTicks<Param>): Twist2dDual<Param> {
        // Calculates the cartesian coordinates of each wheel, where the magnitude is the ticks.
        // TODO: Find n property of kinematics dual numbers, instead of defaulting to 4.
        val rxy = Polar2dDual(DualNum.constant(RED_ANGLE - PI_2, 4), w.red).toCartesian()
        val gxy = Polar2dDual(DualNum.constant(GREEN_ANGLE - PI_2, 4), w.green).toCartesian()
        val bxy = Polar2dDual(DualNum.constant(BLUE_ANGLE - PI_2, 4), w.blue).toCartesian()

        return Twist2dDual(
            // Sum the cartesian coordinates and divide by 1.5 coefficient.
            (rxy + gxy + bxy) / 1.5,
            // Find rotation by summing linear distance and dividing by radius.
            (w.red + w.green + w.blue) / radius,
        )
    }

    /**
     * Represents the velocities of each wheel on the drive.
     */
    data class WheelVelocities<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    ) {
        fun all() = listOf(this.red, this.green, this.blue)
    }

    /**
     * Calculates the velocity of each wheel from the linear and angular velocity of the robot.
     *
     * This is the dual-form of [TriWheels.compute].
     */
    fun <Param> inverse(t: PoseVelocity2dDual<Param>): WheelVelocities<Param> {
        var polar = Polar2dDual.fromCartesian(t.linearVel)

        // If X is negative...
        if (t.linearVel.x.value() < 0) {
            // ...make the radius negative.
            // See https://github.com/BotsBurgh/BOTSBURGH-FTC-2024-25/pull/47#issuecomment-2266043378.
            polar = Polar2dDual(polar.theta, -polar.radius)
        }

        val redAngle = DualNum.constant<Param>(RED_ANGLE, polar.theta.size())
        val greenAngle = DualNum.constant<Param>(GREEN_ANGLE, polar.theta.size())
        val blueAngle = DualNum.constant<Param>(BLUE_ANGLE, polar.theta.size())

        val wheelAngVel = t.angVel / 3.0

        return WheelVelocities(
            polar.radius * (redAngle - polar.theta).sin() + wheelAngVel,
            polar.radius * (greenAngle - polar.theta).sin() + wheelAngVel,
            polar.radius * (blueAngle - polar.theta).sin() + wheelAngVel,
        )
    }

    // Slightly modified from `MecanumKinematics.WheelVelConstraint`.
    inner class WheelVelConstraint(private val maxWheelVel: Double) : VelConstraint {
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
