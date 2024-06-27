package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.TimeTurn
import com.acmerobotics.roadrunner.now
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage

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

        // TODO: Kiwi controller command.

        // TODO: Inverse command for wheel velocities.
        val voltage = Voltage.get()

        // TODO: Create feed-forward.

        // TODO: Calculate wheel power.

        // TODO: Set wheel power.

        // TODO: Draw to FTC Dashboard.

        return true
    }
}
