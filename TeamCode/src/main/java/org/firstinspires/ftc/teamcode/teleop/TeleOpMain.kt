package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Claw
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt




@TeleOp(name = "TeleOpMain")

class TeleOpMain : OpMode() {


    override fun init() {
        TriWheels.init(this)
        ScissorLift.init(this)
        Claw.init(this)

    }

    override fun loop() {
        // joystick(Movement) input
        val joyX = -this.gamepad1.left_stick_x.toDouble()
        val joyY = this.gamepad1.left_stick_y.toDouble()

        //ColorSensor on
        Claw.light()

        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0) - (2.0 * PI / 3.0)

        val joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        val rotationPower = this.gamepad1.right_stick_x.toDouble()

        // movement of all wheels
        TriWheels.drive(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMain.DRIVE_SPEED,
            rotation = rotationPower * RobotConfig.TeleOpMain.ROTATE_SPEED,
        )

        //scissor lift

        ScissorLift.unlift(gamepad2.left_trigger.toDouble())

        ScissorLift.lift(-gamepad2.right_trigger.toDouble())

        if (this.gamepad2.left_stick_button){ //Emergency stop
            ScissorLift.stop()
        }

        //claw movement
        if(gamepad2.dpad_up) {
            Claw.verticalMovePlus()
        }

        if(gamepad2.dpad_down){
            Claw.verticalMoveMinus()
        }

        if(gamepad2.dpad_left){
            Claw.horizontalMoveMinus()
        }

        if(gamepad2.dpad_right){
            Claw.horizontalMovePlus()
        }

        if (this.gamepad2.a){
            Claw.grab()
        }
        if (this.gamepad2.b){
            Claw.release()
        }
        if (this.gamepad2.left_bumper){
            Claw.extend()
        }
        if (this.gamepad2.right_bumper){
            Claw.retract()
        }


    }
}
