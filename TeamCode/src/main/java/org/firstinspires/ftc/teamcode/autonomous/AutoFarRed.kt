package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.RobotConfig


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

@Autonomous(name = "AutoMain - FarRed")
class AutoFarRed : LinearOpMode() {

    override fun runOpMode(){

        waitForStart()

    }

}