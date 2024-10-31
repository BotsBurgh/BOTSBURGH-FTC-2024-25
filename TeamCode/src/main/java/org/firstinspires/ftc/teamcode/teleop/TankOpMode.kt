package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.roadrunner.tank.TankDrive
import kotlin.math.PI

@TeleOp(name = "TANKS MWAHAHA")
class TankOpMode : LinearOpMode() {
    override fun runOpMode() {
        val start = Pose2d(Vector2d(0.0, 0.0), 0.0)
        val drive = TankDrive(this.hardwareMap, start)

        val action =
            drive.actionBuilder(start).splineTo(Vector2d(48.0, 48.0), PI / 2)
                .lineToX(0.0)
                .build()

        runBlocking(action)
    }
}
