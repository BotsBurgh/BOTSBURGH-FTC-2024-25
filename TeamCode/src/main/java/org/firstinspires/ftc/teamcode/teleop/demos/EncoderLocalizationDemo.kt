package org.firstinspires.ftc.teamcode.teleop.demos

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.Polar2d
import kotlin.math.PI

@TeleOp(name = "Encoder Localization Demo", group = "Demo")
@Disabled
class EncoderLocalizationDemo : OpMode() {
    private val driveSpeed = 0.3
    private val wheelDiameter = 3.8
    private val ticksPerRevolution = 145.1

    override fun init() {
        TriWheels.init(this)
        telemetry.addData("Status", "Initialized")
    }

    override fun loop() {
        // Drive robot with left joystick.
        TriWheels.drive(
            Polar2d.fromCartesian(gamepad1.left_stick_x.toDouble() * this.driveSpeed, -gamepad1.left_stick_y.toDouble() * this.driveSpeed),
            0.0,
        )

        // Reset position to (0, 0) when both bumpers are pressed.
        if (gamepad1.left_bumper && gamepad1.right_bumper) {
            TriWheels.stopAndResetMotors()
        }

        // Find positions of each wheel, converting to distance traveled in inches.
        val wheelPositions =
            Triple(
                this.ticksToInch(TriWheels.red.currentPosition),
                this.ticksToInch(TriWheels.green.currentPosition),
                this.ticksToInch(TriWheels.blue.currentPosition),
            )

        // Find position of the entire robot.
        val robotPosition = TriWheels.inverse(wheelPositions)

        val packet = TelemetryPacket()

        // Log information to both FTC Dashboard and telemetry.
        for (f in listOf(packet::put, telemetry::addData)) {
            f("Red", wheelPositions.first)
            f("Green", wheelPositions.second)
            f("Blue", wheelPositions.third)

            f("X", robotPosition.x)
            f("Y", robotPosition.y)
        }

        // Draw robot for FTC Dashboard.
        packet.fieldOverlay().apply {
            setStroke("#3F51B5")
            setStrokeWidth(1)
            strokeCircle(robotPosition.x, robotPosition.y, 9.0)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)

        telemetry.addData("Status", "Running")
    }

    private fun ticksToInch(position: Int) = position.toDouble() / this.ticksPerRevolution * this.wheelDiameter * PI
}
