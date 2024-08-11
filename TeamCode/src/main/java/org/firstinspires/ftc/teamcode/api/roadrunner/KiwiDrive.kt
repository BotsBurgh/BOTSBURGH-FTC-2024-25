package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.core.API

object KiwiDrive : API() {
    override val dependencies = setOf(TriWheels, KiwiLocalizer, Voltage)

    var pose = Pose2d(0.0, 0.0, 0.0)

    fun actionBuilder(beginPose: Pose2d) = TrajectoryActionBuilder(
        ::TurnAction,
        ::FollowTrajectoryAction,
        TODO("trajectory build params"),
        beginPose,
        0.0,
        TODO("turn constraints"),
        TODO("vel constraints"),
        TODO("accel constraints"),
    )

    /** Updates the currently tracked [pose] using [KiwiLocalizer]. */
    fun updatePoseEstimates(): PoseVelocity2d {
        val twist = KiwiLocalizer.update()
        this.pose += twist.value()
        return twist.velocity().value()
    }
}
