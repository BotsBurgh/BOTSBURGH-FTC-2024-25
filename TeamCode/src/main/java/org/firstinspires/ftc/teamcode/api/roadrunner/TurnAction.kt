package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.MotorFeedforward
import com.acmerobotics.roadrunner.TimeTurn
import com.acmerobotics.roadrunner.now
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI

class TurnAction(private val turn: TimeTurn) : Action {
    /**
     * When this action was first run.
     *
     * This is negative if the action has not been initialized.
     */
    private var start: Double = -1.0

    override fun run(p: TelemetryPacket): Boolean {
        val elapsed =
            if (start < 0.0) {
                // First time running, initialize start.
                start = now()
                0.0
            } else {
                // Calculate elapsed time.
                now() - start
            }

        // Exit condition.
        if (elapsed >= this.turn.duration) {
            TriWheels.stop()
            return false
        }

        val target = this.turn[elapsed]

        // TODO: Update localizer (pose estimate) to get current velocity and acceleration

        // TODO: Compute target velocity given a target pose, actual pose, and actual velocity using
        // a KiwiController.

        // TODO: Inverse target velocity into individual wheel velocities.

        // Wheel circumference divided by ticks per revolution.
        val inchesPerTick = (RobotConfig.KiwiDrive.wheelDiameter * PI) / RobotConfig.KiwiDrive.ticksPerRev

        val feedforward =
            MotorFeedforward(
                RobotConfig.KiwiDrive.kS,
                RobotConfig.KiwiDrive.kV / inchesPerTick,
                RobotConfig.KiwiDrive.kA / inchesPerTick,
            )

        // TriWheels.power(
        //     feedforward.compute(TODO("Red velocity")),
        //     feedforward.compute(TODO("Green velocity")),
        //     feedforward.compute(TODO("Blue velocity")),
        // )

        // TODO: Draw to FTC Dashboard.

        return true
    }
}
