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
        val joyX = this.gamepad1.left_stick_x.toDouble()
        val joyY = -this.gamepad1.left_stick_y.toDouble()

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

        ScissorLift.unlift(gamepad1.left_trigger.toDouble())

        ScissorLift.lift(-gamepad1.right_trigger.toDouble())
        var liftPos = ScissorLift.getLockPos()

        if (gamepad1.x && gamepad1.y){ //Emergency stop
            ScissorLift.stop()
        }

        if(ScissorLift.isPowered()){
            ScissorLift.goToPos(liftPos)
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
        if (this.gamepad2.left_bumper) {
            Claw.extend()
        }

        if (this.gamepad2.right_bumper){
            Claw.retract()
        }
        if(this.gamepad1.b){
            RobotConfig.TeleOpMain.ROTATE_SPEED /= RobotConfig.TeleOpMain.SPEED_MODIFIER
            RobotConfig.TeleOpMain.DRIVE_SPEED /= RobotConfig.TeleOpMain.SPEED_MODIFIER
        }

        if(this.gamepad1.a){
            RobotConfig.TeleOpMain.ROTATE_SPEED *= RobotConfig.TeleOpMain.SPEED_MODIFIER
            RobotConfig.TeleOpMain.DRIVE_SPEED *= RobotConfig.TeleOpMain.SPEED_MODIFIER
        }



        //debug telemetry
        telemetry.addData("Current", Triple(
                TriWheels.red.currentPosition,
                TriWheels.green.currentPosition,
                TriWheels.blue.currentPosition,
            ),
        )

        telemetry.update()


    }
}
