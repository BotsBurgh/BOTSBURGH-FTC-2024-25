package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.TankWheel

@TeleOp(name = "Main Quad")
class TeleOpMainTank : OpMode() {
    override fun init() {
        TankWheel.init(this)
    }

    override fun loop() {
        // movement of all wheels
        TankWheel.drive()
    }
}
