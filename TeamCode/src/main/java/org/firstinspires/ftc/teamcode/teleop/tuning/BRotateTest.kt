package org.firstinspires.ftc.teamcode.teleop.tuning

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Time
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiKinematics
import org.firstinspires.ftc.teamcode.RobotConfig

@TeleOp(name = "Rotate Test", group = GROUP)
@Disabled
class BRotateTest : LinearOpMode() {
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

        val initialPosition =
            Triple(
                TriWheels.red.currentPosition,
                TriWheels.green.currentPosition,
                TriWheels.blue.currentPosition,
            )

        while (opModeIsActive()) {
            val kinematics = KiwiKinematics(RobotConfig.KiwiLocalizer.RADIUS)

            val currentPosition =
                Triple(
                    (TriWheels.red.currentPosition - initialPosition.first) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                    (TriWheels.green.currentPosition - initialPosition.second) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                    (TriWheels.blue.currentPosition - initialPosition.third) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                )

            val twist =
                kinematics.forward(
                    KiwiKinematics.WheelTicks<Time>(
                        DualNum.constant(currentPosition.first, 1),
                        DualNum.constant(currentPosition.second, 1),
                        DualNum.constant(currentPosition.third, 1),
                    ),
                )

            val angle = twist.angle.value()

            this.telemetry.apply {
                addData("Status", "Running")
                addData("Angle", angle)
                update()
            }
        }
    }
}
