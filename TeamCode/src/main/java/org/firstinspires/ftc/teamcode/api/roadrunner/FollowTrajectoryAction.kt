package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.TimeTrajectory
import com.acmerobotics.roadrunner.now
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage

/**
 * An [Action] used to make a [KiwiDrive] follow a trajectory.
 */
class FollowTrajectoryAction(private val timeTrajectory: TimeTrajectory) : Action {
    private var start = -1.0

    override fun run(p: TelemetryPacket): Boolean {
        // Calculate the elapsed time, initializing `start` if it hasn't been yet.
        val elapsed =
            if (start < 0.0) {
                start = now()
                0.0
            } else {
                now() - start
            }

        if (elapsed >= this.timeTrajectory.duration) {
            TriWheels.stop()
            return false
        }

        // Find where the robot should be for the current time.
        val targetPose = this.timeTrajectory[elapsed]

        // Update the actual position and velocity.
        val actualVel = KiwiDrive.updatePoseEstimates()

        // Compute what velocity we should set the robot to.
        val commandVel = KiwiDrive.controller.compute(targetPose, KiwiDrive.pose, actualVel)

        // Compute the individual velocities for each wheel of the robot.
        val wheelCommandVel = KiwiLocalizer.kinematics.inverse(commandVel)

        val voltage = Voltage.get()

        // Compute the individual power fed to each wheel.
        val redPower = KiwiDrive.feedforward.compute(wheelCommandVel.red) / voltage
        val greenPower = KiwiDrive.feedforward.compute(wheelCommandVel.green) / voltage
        val bluePower = KiwiDrive.feedforward.compute(wheelCommandVel.blue) / voltage

        TriWheels.power(redPower, greenPower, bluePower)

        // TODO: Draw robot on FTC Dashboard.

        return true
    }
}
