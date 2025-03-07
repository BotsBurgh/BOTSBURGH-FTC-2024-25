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

        //Drive to bar
        Otos.otosDrive(RobotConfig.OTOS.POS[0], RobotConfig.OTOS.POS[1], RobotConfig.OTOS.POS[2], RobotConfig.OTOS.POS[3])
        sleep(100)
        //@TODO CLIP
        ScissorLift.goToPos(RobotConfig.Scissorlift.CLIPPABLE)
        Claw.open()
        Claw.release()
        Claw.close()
       //Align to push block 1
        Otos.otosDrive(RobotConfig.OTOS.POS2[0], RobotConfig.OTOS.POS2[1], RobotConfig.OTOS.POS2[2], RobotConfig.OTOS.POS2[3])
        sleep(100)
       //Otos.turn(RobotConfig.OTOS.TURN180[0], RobotConfig.OTOS.TURN180[1])
        sleep(100)
        Otos.otosDrive(RobotConfig.OTOS.POS3[0], RobotConfig.OTOS.POS3[1], RobotConfig.OTOS.POS3[2], RobotConfig.OTOS.POS3[3])
        sleep(100)
        Otos.otosDrive(RobotConfig.OTOS.POS4[0], RobotConfig.OTOS.POS4[1], RobotConfig.OTOS.POS4[2], RobotConfig.OTOS.POS4[3])
        sleep(100)
        Otos.otosDrive(RobotConfig.OTOS.POS5[0], RobotConfig.OTOS.POS5[1], RobotConfig.OTOS.POS5[2], RobotConfig.OTOS.POS5[3])
        sleep(100)

        Otos.otosDrive(RobotConfig.OTOS.POS6[0], RobotConfig.OTOS.POS6[1], RobotConfig.OTOS.POS6[2], RobotConfig.OTOS.POS6[3])
        sleep(100)
        //@TODO GRAB


        //Drive to bar
        //Otos.turn(RobotConfig.OTOS.TURN0[0], RobotConfig.OTOS.TURN0[1])
        sleep(100)

        //CLIP
        Claw.open()
        Claw.release()
        Claw.close()

        Otos.otosDrive(RobotConfig.OTOS.POS7[0], RobotConfig.OTOS.POS7[1], RobotConfig.OTOS.POS7[2], RobotConfig.OTOS.POS7[3])
        sleep(100)
        //Otos.turn(RobotConfig.OTOS.TURN180[0], RobotConfig.OTOS.TURN180[1])
        sleep(100)

        //Drive to wall
        Otos.otosDrive(RobotConfig.OTOS.POS8[0], RobotConfig.OTOS.POS8[1], RobotConfig.OTOS.POS8[2], RobotConfig.OTOS.POS8[3])
        sleep(100)
        //@TODO GRAB
        //Otos.turn(RobotConfig.OTOS.TURN0[0], RobotConfig.OTOS.TURN0[1])
        sleep(100)

        //Drive to bar
        Otos.otosDrive(RobotConfig.OTOS.POS9[0], RobotConfig.OTOS.POS9[1], RobotConfig.OTOS.POS9[2], RobotConfig.OTOS.POS9[3])
        sleep(100)

        //Clip
        Claw.open()
        Claw.release()
        Claw.close()

        //Park
        Otos.otosDrive(RobotConfig.OTOS.POS10[0], RobotConfig.OTOS.POS10[1], RobotConfig.OTOS.POS10[2], RobotConfig.OTOS.POS10[3])

    }
}