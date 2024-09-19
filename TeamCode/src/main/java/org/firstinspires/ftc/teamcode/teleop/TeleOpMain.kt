package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.QuadWheels


@TeleOp(name = "Main")
class TeleOpMain : OpMode() {
    override fun init() {
        QuadWheels.init(this)
    }

    override fun loop() {
        // movement of all wheels
        QuadWheels.drive()
    }
}
