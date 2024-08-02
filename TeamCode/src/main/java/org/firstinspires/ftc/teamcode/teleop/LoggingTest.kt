package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.Logging

@TeleOp(name = "Logging Test")
class LoggingTest : OpMode() {
    private var wheel: Logging = Logging

    private var distance: Logging = Logging

    private var dist: Double = 0.0

    override fun init() {
        Logging.init(this)

        wheel.createFile("Wheel Power")
        distance.createFile("Distance")

        wheel.writeFile(arrayOf(0.25, 0.6, 1.0))
        distance.writeFile(1.0)


    }

    override fun loop() {
        distance.writeFile(dist)
        dist += 5
    }

    override fun stop() {
        wheel.close()
        distance.close()
    }
}
