package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.RobotConfig
import android.os.CountDownTimer

/**
 * Turn left
 * Drive a little
 * Turn Straight
 * Raise Scissorlift to val
 * Raise Claw to val
 * Drive to cage
 * place claw on cage
 * drive back
 * If applicable, get all 3 ground samples and drag back to zone
 * Park
 */

@Autonomous(name = "AutoMain - CloseBlue")
    class AutoCloseBlue : LinearOpMode() {
        private var forward = Encoders.Direction.Red

        private var side = "Blue"

        override fun runOpMode(){

            TriWheels.init(this)
            Encoders.init(this)
            Claw.init(this)
            ScissorLift.init(this)

            waitForStart()

            Encoders.spinTo(-RobotConfig.MainAuto.CFIRST_TURN)
            Encoders.driveTo(forward, RobotConfig.MainAuto.CFIRST_MOVE)
            Encoders.spinTo(RobotConfig.MainAuto.CFIRST_TURN)
            ScissorLift.lift(1.0)
            Encoders.driveTo(forward, RobotConfig.MainAuto.CTO_CAGE)




        }

    }