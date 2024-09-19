package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.QuadWheels
import org.firstinspires.ftc.teamcode.api.TriWheels
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

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
