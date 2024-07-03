package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.DemoAPI
import org.firstinspires.ftc.teamcode.api.linear.DemoLinearAPI

@TeleOp(name = "Demo TeleOp", group = "Demo")
@Disabled
class DemoTeleOp : OpMode() {
    // Run once, when "Init" is pressed on driver hub.
    override fun init() {
        // Initializes an API, shared code used by components.
        DemoAPI.init(this)

        // Initialize the April Vision API
        DemoLinearAPI.init(this)
    }

    // Run repeatedly while the robot is running.
    override fun loop() {
        val x = this.gamepad1.left_stick_x.toDouble()
        val y = this.gamepad1.left_stick_y.toDouble()
        val rotation = this.gamepad1.right_stick_x.toDouble()

        // Strafe + rotate robot using left and right joysticks.
        DemoAPI.drive(
            x - y + rotation,
            -x - y - rotation,
            -x - y + rotation,
            x - y - rotation,
        )
    }
}
