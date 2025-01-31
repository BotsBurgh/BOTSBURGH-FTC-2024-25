package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.ScissorLift
import org.firstinspires.ftc.teamcode.RobotConfig
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

    class AutoCloseSimple : LinearOpMode() {
        private var forward = Encoders.Direction.Red

        private var tile = 24.0 //One tile in inches



        override fun runOpMode() {
            TriWheels.init(this)
            Encoders.init(this)
            Claw.init(this)
            ScissorLift.init(this)

            waitForStart()


                Encoders.spinTo(RobotConfig.CloseAutonomous.FIRST_TURN)
                Encoders.driveTo(forward, RobotConfig.CloseAutonomous.FORWARD)
                Claw.verticalMovePlus()
                Encoders.spinTo(-RobotConfig.CloseAutonomous.SMALL_TURN)
                Claw.grab()
                Claw.release()
                //Stop here ig
        }
    }


