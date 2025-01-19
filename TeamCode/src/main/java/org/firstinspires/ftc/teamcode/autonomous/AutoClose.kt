package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.ScissorLift
/**
 * This is the main code for the autonomous segment of the game.
 *
 * Move to the side
 * Drop Sample in Zone
 * Move to Floor Sample
 * Drop in zone
 * REPEAT!
 */
@Autonomous(name = "AutoMain - CloseToBasket")

    class AutoClose : LinearOpMode() {
        private var forward = Encoders.Direction.Red

        private var tile = 24.0 //One tile in inches



        override fun runOpMode() {
            TriWheels.init(this)
            Encoders.init(this)
            Claw.init(this)
            ScissorLift.init(this)

            waitForStart()

                //FIXME: Change Values as needed + Claw Business(If komel ever finishes it)

                Encoders.spinTo(-90.0)
                Encoders.driveTo(forward, tile)
                Claw.release()
                Encoders.driveTo(forward, tile)
                //Stop here ig
        }
    }


