package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Otos
import org.firstinspires.ftc.teamcode.api.linear.Otos.linearOpMode

@Autonomous(name = "OTOS Test")

class OTOS : LinearOpMode() {

    override fun runOpMode() {
        TriWheels.init(this)
        Otos.init(this)
        ScissorLift.init(this)
        Claw.init(this)

        Otos.configureOtos()

//        while(true) {
//            with(telemetry) {
//                addData("current X coordinate", Otos.myPos());
//                update();
//            }
//        }

        waitForStart()

        Otos.otosDrive(RobotConfig.OTOS.POS[0], RobotConfig.OTOS.POS[1], RobotConfig.OTOS.POS[2], RobotConfig.OTOS.POS[3])
        sleep(100)
        ScissorLift.lift(1.0)
        sleep(1000)
        ScissorLift.stop()
        sleep(2000)
        Claw.largeMoveToPos(0.5)
        sleep(2000)
        Claw.smallMoveToPos(1.0)
        sleep(2000)
        ScissorLift.lift(-1.0)
        sleep(500)
        ScissorLift.stop()
        Claw.grab()
        sleep(1000)
        Claw.open()
        sleep(2000)
        Otos.otosDrive(RobotConfig.OTOS.POS2[0], RobotConfig.OTOS.POS2[1], RobotConfig.OTOS.POS2[2], RobotConfig.OTOS.POS2[3])
        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS3[0], RobotConfig.OTOS.POS3[1], RobotConfig.OTOS.POS3[2], RobotConfig.OTOS.POS3[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS4[0], RobotConfig.OTOS.POS4[1], RobotConfig.OTOS.POS4[2], RobotConfig.OTOS.POS4[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS5[0], RobotConfig.OTOS.POS5[1], RobotConfig.OTOS.POS5[2], RobotConfig.OTOS.POS5[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS6[0], RobotConfig.OTOS.POS6[1], RobotConfig.OTOS.POS6[2], RobotConfig.OTOS.POS6[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS7[0], RobotConfig.OTOS.POS7[1], RobotConfig.OTOS.POS7[2], RobotConfig.OTOS.POS7[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS8[0], RobotConfig.OTOS.POS8[1], RobotConfig.OTOS.POS8[2], RobotConfig.OTOS.POS8[3])
//        sleep(100)
//        Otos.otosDrive(RobotConfig.OTOS.POS9[0], RobotConfig.OTOS.POS9[1], RobotConfig.OTOS.POS9[2], RobotConfig.OTOS.POS9[3])
//        sleep(1000)
//        Otos.otosDrive(RobotConfig.OTOS.POS10[0], RobotConfig.OTOS.POS10[1], RobotConfig.OTOS.POS10[2], RobotConfig.OTOS.POS10[3])

    }
}