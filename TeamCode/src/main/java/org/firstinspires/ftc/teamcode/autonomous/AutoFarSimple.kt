package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.RobotConfig
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
@Autonomous(name = "AutoSimple - FarFromBasket")

class AutoFarSimple : LinearOpMode() {
    private var forward = Encoders.Direction.Red

    private var tile = 24.0 //One tile in inches



    override fun runOpMode() {
        TriWheels.init(this)
        Encoders.init(this)
        Claw.init(this)
        ScissorLift.init(this)

        waitForStart()


        Encoders.driveTo(forward, RobotConfig.SimpleAutonomous.FORWARD)
        telemetry.addLine("forward")
        telemetry.update()
        sleep(1000)
        Claw.verticalMovePlus()
        telemetry.addLine("arm")
        telemetry.update()
        sleep(1000)
        Claw.grab()
        telemetry.addLine("eject")
        telemetry.update()
        sleep(1000)
        //Stop here ig
    }
}
