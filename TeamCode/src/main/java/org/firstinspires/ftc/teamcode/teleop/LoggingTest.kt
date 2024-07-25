package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.api.Logging
import org.firstinspires.ftc.teamcode.api.Telemetry
import java.io.File

@TeleOp(name = "Logging Test")
class LoggingTest : OpMode() {
    private var wheel: Logging = Logging
    override fun init() {
        Logging.init(this)



        wheel.createFile("Wheel Power")

        wheel.writeFile(arrayOf(0.25, 0.6, 1.0))

        wheel.writeFile(0.5)

    }

    override fun loop() {}

    override fun stop() {
        wheel.close()
    }
}