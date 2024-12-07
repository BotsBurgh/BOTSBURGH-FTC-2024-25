package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders

/**
 * This is the main code for the autonomous segment of the game.
 *
 * This literally rams the robot forward
 */
@Autonomous(name = "Main")
class AutoMain : LinearOpMode() {
    private var forward = Encoders.Direction.Green

    // This function is the entrypoint into our code. It is run when the "Init" button is pressed.
    override fun runOpMode() {
        // Initialization
        TriWheels.init(this)
        Encoders.init(this)

        waitForStart()
        // Start
        Encoders.driveTo(forward, 50.0)
    }
}
