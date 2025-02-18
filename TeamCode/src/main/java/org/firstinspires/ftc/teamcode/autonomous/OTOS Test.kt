package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Otos
import org.firstinspires.ftc.teamcode.api.linear.Otos.linearOpMode

@Autonomous(name = "OTOS Test")

class OTOS : LinearOpMode() {

    override fun runOpMode() {
        TriWheels.init(this)
        Otos.init(this)

        Otos.otos

//        while(true) {
//            with(telemetry) {
//                addData("current X coordinate", Otos.myPos());
//                update();
//            }
//        }

        waitForStart()

        Otos.otosDrive(0.0, 10.0 , 0.0, 10.0)
    }
}