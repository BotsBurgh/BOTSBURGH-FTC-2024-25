package org.firstinspires.ftc.teamcode.teleop.tuning

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * # Step A: Forward Push Test
 *
 * This goal of this test is to determine [RobotConfig.KiwiLocalizer.INCHES_PER_TICK] empirically.
 *
 * ## Instructions
 *
 * 1. Square up the red wheel of the robot to the grid of the field.
 * 2. Slowly push the robot forward so that the red wheel rotates some distance, such as 6 feet.
 * 3. Record the outputted "Ticks traveled" from the telemetry.
 * 4. Set `INCHES_PER_TICK` to the real distance in inches / ticks traveled.
 *
 * While performing this process, make sure the wheels don't slip and that the robot travels in a
 * straight line. You can fact check your result by running the test again after setting
 * `INCHES_PER_TICK` and verifying the telemetry output for "Inches traveled".
 *
 * You may also calculate inches per tick from the wheel radius, motor specification, and gear
 * ratio. You should still check it using this test, though.
 */
@TeleOp(name = "Forward Push Test", group = GROUP)
@Disabled
class AForwardPushTest : LinearOpMode() {
    // Change this if you want to track the ticks traveled for a different wheel.
    private val trackedWheel
        get() = TriWheels.red

    override fun runOpMode() {
        TriWheels.init(this)

        // Make the wheels float so that they spin freely. They're being pushed!
        for (wheel in TriWheels.wheels()) {
            wheel.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }

        this.telemetry.apply {
            addData("Status", "Initialized")
            update()
        }

        waitForStart()

        val initialPosition = this.trackedWheel.currentPosition

        while (opModeIsActive()) {
            val currentPosition = this.trackedWheel.currentPosition - initialPosition

            this.telemetry.apply {
                addData("Status", "Running")
                addData("Ticks traveled", currentPosition)
                addData("Inches per tick", RobotConfig.KiwiLocalizer.INCHES_PER_TICK)
                addData("Inches traveled", currentPosition.toDouble() * RobotConfig.KiwiLocalizer.INCHES_PER_TICK)
                update()
            }
        }
    }
}
