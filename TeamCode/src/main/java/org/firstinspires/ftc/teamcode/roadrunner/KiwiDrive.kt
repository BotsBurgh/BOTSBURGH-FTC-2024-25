package org.firstinspires.ftc.teamcode.roadrunner

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TurnConstraints
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.PI

class KiwiDrive(val hardwareMap: HardwareMap, val pos: Pose2d) {
    private val turnConstraints = TurnConstraints(
        // Max velocity in radians. (Min is 0.)
        PI,
        // Min acceleration in radians.
        -PI,
        // Max acceleration in radians.
        PI,
    )

    fun actionBuilder(beginPos: Pose2d): TrajectoryActionBuilder {
        return TrajectoryActionBuilder(
            TODO("Turn action"),
            TODO("Follow (trajectory) action"),
            TODO("Trajectory builder params"),
            beginPos,
            0.0,
            this.turnConstraints,
            TODO("Velocity constraints"),
            TODO("Acceleration constraints"),
        )
    }
}
