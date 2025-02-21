package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Claw
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt




@TeleOp(name = "TeleOpMain")

class TeleOpMain : OpMode() {

    var bigPos : Double = 0.0
    var smallPos : Double = 0.0

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

        var isSpeedModded = false

        // movement of all wheels
        TriWheels.drive(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMain.DRIVE_SPEED,
            rotation = rotationPower * RobotConfig.TeleOpMain.ROTATE_SPEED,
        )

        //scissor lift

        ScissorLift.unlift(gamepad1.left_trigger.toDouble())

        ScissorLift.lift(-gamepad1.right_trigger.toDouble())

//        var liftPos = ScissorLift.getLockPos()

        if (gamepad1.x && gamepad1.y){ //Emergency stop
            ScissorLift.stop()
        }

//        if(ScissorLift.isPowered()){
//            ScissorLift.goToPos(liftPos)
//        }



        //claw movement

        if(gamepad2.right_bumper) {
            var pos = Claw.smallServo.position
            pos += RobotConfig.Claw.SMALL_INCRIMENT
            smallPos = min(pos, RobotConfig.Claw.SMALL_MAXIMUM_POSITION);

        }

        if(gamepad2.left_bumper){
            var pos = Claw.smallServo.position
            pos -= RobotConfig.Claw.SMALL_INCRIMENT
            smallPos = max(pos, RobotConfig.Claw.SMALL_MINIMUM_POSITION);

        }

        Claw.smallMoveToPos(smallPos)

        if(gamepad2.dpad_left){
            var pos = Claw.largeServo.position
            pos += RobotConfig.Claw.LARGE_INCRIMENT
            smallPos = min(pos, RobotConfig.Claw.LARGE_MAXIMUM_POSITION);
        }

        if(gamepad2.dpad_right){
            var pos = Claw.largeServo.position
            pos -= RobotConfig.Claw.LARGE_INCRIMENT
            smallPos = max(pos, RobotConfig.Claw.LARGE_MINIMUM_POSITION);
        }

        Claw.largeMoveToPos(bigPos)


        if (this.gamepad2.a){
            Claw.grab()
        }
        if (this.gamepad2.b){
            Claw.release()
        }

        if(this.gamepad1.b){

            TriWheels.halfSpeed(isSpeedModded)
            telemetry.addLine("1/2 Speed")
            telemetry.update()
            isSpeedModded=true
        }

        if(this.gamepad1.a){
            TriWheels.halfSpeed(isSpeedModded)
            telemetry.addLine("Full Speed")
            telemetry.update()
            isSpeedModded=false
        }

        if(this.gamepad2.x){
            Claw.close()
        }

        if(this.gamepad2.y){
            Claw.open()
        }



        //servos
        telemetry.addData("small Servo Pos",Claw.smallServo.position)
        telemetry.addData("Large Servo Pos", Claw.largeServo.position)

        telemetry.update()


    }
}
