package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.AngularVelConstraint
import com.acmerobotics.roadrunner.HolonomicController
import com.acmerobotics.roadrunner.MinVelConstraint
import com.acmerobotics.roadrunner.MotorFeedforward
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.ProfileAccelConstraint
import com.acmerobotics.roadrunner.ProfileParams
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TrajectoryBuilderParams
import com.acmerobotics.roadrunner.TurnConstraints
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig

object KiwiDrive : API() {
    override val dependencies = setOf(TriWheels, KiwiLocalizer, Voltage)

    var pose = Pose2d(0.0, 0.0, 0.0)

    val controller =
        HolonomicController(
            RobotConfig.KiwiDrive.AXIAL_GAIN,
            RobotConfig.KiwiDrive.AXIAL_GAIN,
            RobotConfig.KiwiDrive.HEADING_GAIN,
            RobotConfig.KiwiDrive.AXIAL_VEL_GAIN,
            RobotConfig.KiwiDrive.AXIAL_VEL_GAIN,
            RobotConfig.KiwiDrive.HEADING_VEL_GAIN,
        )

    val feedforward =
        MotorFeedforward(
            RobotConfig.KiwiDrive.K_S,
            RobotConfig.KiwiDrive.K_V,
            RobotConfig.KiwiDrive.K_A,
        )

    // This was taken directly from `MecanumDrive`. It shouldn't be changed unless you know what
    // you're doing.
    private val trajectoryBuilderParams =
        TrajectoryBuilderParams(
            arcLengthSamplingEps = 1e-6,
            ProfileParams(
                dispResolution = 0.25,
                angResolution = 0.1,
                angSamplingEps = 1e-2,
            ),
        )

    // TODO: Make constraints update with FTC Dashboard.
    private val turnConstraint =
        TurnConstraints(
            RobotConfig.KiwiDrive.MAX_ANG_VEL,
            -RobotConfig.KiwiDrive.MAX_ANG_ACCEL,
            RobotConfig.KiwiDrive.MAX_ANG_ACCEL,
        )

    private val velConstraint =
        MinVelConstraint(
            listOf(
                KiwiLocalizer.kinematics.WheelVelConstraint(RobotConfig.KiwiDrive.MAX_WHEEL_VEL),
                AngularVelConstraint(RobotConfig.KiwiDrive.MAX_ANG_VEL),
            ),
        )

    private val accelConstraint =
        ProfileAccelConstraint(
            RobotConfig.KiwiDrive.MIN_PROFILE_ACCEL,
            RobotConfig.KiwiDrive.MAX_PROFILE_ACCEL,
        )

    fun actionBuilder(beginPose: Pose2d) =
        TrajectoryActionBuilder(
            ::TurnAction,
            ::FollowTrajectoryAction,
            this.trajectoryBuilderParams,
            beginPose,
            // Robot should end stopped.
            beginEndVel = 0.0,
            this.turnConstraint,
            this.velConstraint,
            this.accelConstraint,
        )

    /** Updates the currently tracked [pose] using [KiwiLocalizer]. */
    fun updatePoseEstimates(): PoseVelocity2d {
        val twist = KiwiLocalizer.update()
        this.pose += twist.value()
        return twist.velocity().value()
    }
}
