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
@Autonomous(name = "AutoMain")
class AutoMain : LinearOpMode() {
    private var forward = Encoders.Direction.Green

    override fun runOpMode() {
        TriWheels.init(this)
        Encoders.init(this)

        waitForStart()

        val start = this.runtime

        TriWheels.drive(0.0, 0.5)

        while (opModeIsActive() && this.runtime - start < 1.5) {
            this.idle()
        }

        TriWheels.stop()
    }
}
