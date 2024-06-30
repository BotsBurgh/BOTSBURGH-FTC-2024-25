package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.AccelConstraint
import com.acmerobotics.roadrunner.AngularVelConstraint
import com.acmerobotics.roadrunner.MinVelConstraint
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.ProfileAccelConstraint
import com.acmerobotics.roadrunner.ProfileParams
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TrajectoryBuilderParams
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.TurnConstraints
import com.acmerobotics.roadrunner.VelConstraint
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig

object KiwiDrive : API() {
    override val dependencies = setOf(TriWheels, KiwiLocalizer)

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
            trajectoryBuilderParams,
            beginPos,
            // Robot should end stopped.
            0.0,
            turnConstraints,
            velConstraints,
            accelConstraints,
        )
    }
}
