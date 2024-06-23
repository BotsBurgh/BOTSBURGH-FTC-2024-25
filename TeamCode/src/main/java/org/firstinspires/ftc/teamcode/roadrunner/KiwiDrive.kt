package org.firstinspires.ftc.teamcode.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.AngularVelConstraint
import com.acmerobotics.roadrunner.MinVelConstraint
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.ProfileAccelConstraint
import com.acmerobotics.roadrunner.ProfileParams
import com.acmerobotics.roadrunner.TimeTurn
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TrajectoryBuilderParams
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.VelConstraint
import com.acmerobotics.roadrunner.now
import kotlin.math.PI

class KiwiDrive(config: Config, private val pos: Pose2d) {
    data class Config(
        /** Minimum translational velocity, in in/s. */
        val minTransVel: Double = 20.0,
        /** Minimum profile acceleration, in in/s^2. */
        val minProfileAccel: Double = -30.0,
        /** Maximum profile acceleration, in in/s^2. */
        val maxProfileAccel: Double = 50.0,
        /** Maximum angular velocity, in rad/s. */
        val maxAngVel: Double = PI,
        /** Maximum angular acceleration, in rad/s^2. */
        val maxAngAccel: Double = PI,
    )

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

            // TODO: Exit condition after certain amount of time has passed, stopping wheels.

            val target = this.turn[elapsed]
            // TODO: Writer shenanigans.

            // TODO: Kiwi controller command.
            // TODO: Writer shenanigans.

            // TODO: Inverse command for wheel velocities.
            // TODO: Get voltage.

            // TODO: Create feed-forward.

            // TODO: Calculate wheel power.
            // TODO: Writer shenanigans.

            // TODO: Set wheel power.

            // TODO: Draw to FTC Dashboard.

            return true
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
            config.maxAngVel,
            -config.maxAngAccel,
            config.maxAngAccel,
        )

    private val velConstraints: VelConstraint =
        MinVelConstraint(
            listOf(
                // TODO: Switch to wheel constraints.
                TranslationalVelConstraint(config.minTransVel),
                AngularVelConstraint(config.maxAngVel),
            ),
        )

    private val accelConstraints: AccelConstraint =
        ProfileAccelConstraint(
            config.minProfileAccel,
            config.maxProfileAccel,
        )

    fun actionBuilder(beginPos: Pose2d): TrajectoryActionBuilder {
        return TrajectoryActionBuilder(
            TODO("Turn action"),
            TODO("Follow (trajectory) action"),
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
