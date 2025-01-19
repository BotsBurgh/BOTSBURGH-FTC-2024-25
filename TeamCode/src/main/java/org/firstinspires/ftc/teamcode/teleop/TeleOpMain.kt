package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Claw
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt
import com.qualcomm.robotcore.hardware.TouchSensor



@TeleOp(name = "TeleOpMain")

class TeleOpMain : OpMode() {
    lateinit var Minlift : TouchSensor
    lateinit var Maxlift : TouchSensor

    override fun init() {
        TriWheels.init(this)
        ScissorLift.init(this)
        Claw.init(this)

        this.Minlift = this.hardwareMap.get(TouchSensor::class.java, "MinLift")
        this.Maxlift = this.hardwareMap.get(TouchSensor::class.java, "MaxLift")
    }

    override fun loop() {
        // joystick(Movement) input
        val joyX = -this.gamepad1.left_stick_x.toDouble()
        val joyY = this.gamepad1.left_stick_y.toDouble()

        //joystick(Claw) input
        val xPos = this.gamepad2.left_stick_x.toDouble()
        val yPos = this.gamepad2.left_stick_x.toDouble()

        //ColorSensor on
        Claw.light()

        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0) - (2.0 * PI / 3.0)

        val joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        val rotationPower = -this.gamepad1.right_stick_x.toDouble()

        // movement of all wheels
        TriWheels.drive(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMain.DRIVE_SPEED,
            rotation = rotationPower * RobotConfig.TeleOpMain.ROTATE_SPEED,
        )

        //scissor lift


        if (this.gamepad2.left_trigger < 0.5 && !Maxlift.isPressed) { //Made at 0.5 so that accidental presses dont trigger
            ScissorLift.lift()
        }
        if (this.gamepad2.right_trigger < 0.5 && !Minlift.isPressed) {
            ScissorLift.unlift()
        }
        if (this.gamepad2.left_stick_button){ //Emergency stop
            ScissorLift.stop()
        }

        //claw movement
        Claw.horizontalMove(xPos)

        Claw.verticalMove(yPos)

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
