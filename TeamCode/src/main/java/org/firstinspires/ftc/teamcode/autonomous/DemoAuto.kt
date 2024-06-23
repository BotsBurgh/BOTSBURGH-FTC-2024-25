package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.api.DemoAPI

@Autonomous(name = "Demo Autonomous")
@Disabled
class DemoAutonomous : LinearOpMode() {
    override fun runOpMode() {

        // APIs are initialized here
        DemoAPI.init(this)

        // Wait for the run button to be pressed
        waitForStart()

        val runtime = ElapsedTime()

        while (runtime.seconds() < 2.0) {
            // Drive at 50% speed, slowing down the closer we get to 2 seconds.
            DemoAPI.drive(0.5 * (2.0 - runtime.seconds()))
        }

        // Stop the robot.
        DemoAPI.drive(0.0)
    }
}