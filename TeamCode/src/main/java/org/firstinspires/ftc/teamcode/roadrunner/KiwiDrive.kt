package org.firstinspires.ftc.teamcode.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.AngularVelConstraint
import com.acmerobotics.roadrunner.MinVelConstraint
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.ProfileAccelConstraint
import com.acmerobotics.roadrunner.ProfileParams
import com.acmerobotics.roadrunner.TimeTrajectory
import com.acmerobotics.roadrunner.TimeTurn
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TrajectoryBuilderParams
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.VelConstraint
import com.acmerobotics.roadrunner.now
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.utils.RobotConfig

class KiwiDrive {
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

    class FollowTrajectoryAction(private val trajectory: TimeTrajectory) : Action {
        override fun run(p: TelemetryPacket): Boolean {
            TODO()
        }
    }

    // Values copied from `MecanumDrive`.
    private val trajectoryBuilderParams =
        TrajectoryBuilderParams(
            1e-6,
            ProfileParams(0.25, 0.1, 1e-2),
        )

    private val turnConstraints =
        TurnConstraints(
            RobotConfig.KiwiDrive.maxAngVel,
            -RobotConfig.KiwiDrive.maxAngAccel,
            RobotConfig.KiwiDrive.maxAngAccel,
        )

    private val velConstraints: VelConstraint =
        MinVelConstraint(
            listOf(
                // TODO: Switch to wheel constraints.
                TranslationalVelConstraint(RobotConfig.KiwiDrive.minTransVel),
                AngularVelConstraint(RobotConfig.KiwiDrive.maxAngVel),
            ),
        )

    private val accelConstraints: AccelConstraint =
        ProfileAccelConstraint(
            RobotConfig.KiwiDrive.minProfileAccel,
            RobotConfig.KiwiDrive.maxProfileAccel,
        )

    fun actionBuilder(beginPos: Pose2d): TrajectoryActionBuilder {
        return TrajectoryActionBuilder(
            ::TurnAction,
            ::FollowTrajectoryAction,
            this.trajectoryBuilderParams,
            beginPos,
            // Robot should end stopped.
            0.0,
            this.turnConstraints,
            this.velConstraints,
            this.accelConstraints,
        )
    }
}
