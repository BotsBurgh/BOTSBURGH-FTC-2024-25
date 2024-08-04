package org.firstinspires.ftc.teamcode.teleop

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.Logging

@TeleOp(name = "Logging Test")
class LoggingTest : OpMode() {
    private var dist: Double = 0.0

    override fun init() {
        Logging.init(this)

        Logging.createFile("Wheel Power")
        Logging.createFile("Distance")

        Logging.writeFile("Wheel Power", arrayOf(0.25, 0.6, 1.0))
        Logging.writeFile("Distance", 1.0)


    }

    override fun loop() {
        Logging.writeFile("Distance", dist)
        dist += 5
    }

    override fun stop() {
        Logging.close("Distance")
        Logging.close("Wheel Power")
    }
}
