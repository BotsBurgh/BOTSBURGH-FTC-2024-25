package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.TimeTrajectory

class FollowTrajectoryAction(private val trajectory: TimeTrajectory) : Action {
    override fun run(p: TelemetryPacket): Boolean {
        TODO()
    }
}
