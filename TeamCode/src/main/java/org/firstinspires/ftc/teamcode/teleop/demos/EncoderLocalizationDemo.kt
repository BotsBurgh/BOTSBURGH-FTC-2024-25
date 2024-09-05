package org.firstinspires.ftc.teamcode.teleop.demos

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Rotation2d
import com.acmerobotics.roadrunner.Twist2d
import com.acmerobotics.roadrunner.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.TriWheels.RED_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.GREEN_ANGLE
import org.firstinspires.ftc.teamcode.api.TriWheels.BLUE_ANGLE
import org.firstinspires.ftc.teamcode.utils.PI_2
import org.firstinspires.ftc.teamcode.utils.Polar2d
import kotlin.math.PI

@TeleOp(name = "Encoder Localization Demo", group = "Demo")
//@Disabled
class EncoderLocalizationDemo : OpMode() {
    private val driveSpeed = 0.3
    private val wheelDiameter = 12.0
    private val inchesPerTick = 0.082191780821918

    private val wheelRadius = this.wheelDiameter / 2.0

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
                TriWheels.red.currentPosition.toDouble() * this.inchesPerTick,
                TriWheels.green.currentPosition.toDouble() * this.inchesPerTick,
                TriWheels.blue.currentPosition.toDouble() * this.inchesPerTick,
            )

        // Find position of the entire robot.
        val (robotPosition, robotRotation) = this.inverse(wheelPositions)

        val packet = TelemetryPacket()

        // Log information to both FTC Dashboard and telemetry.
        for (f in listOf(packet::put, telemetry::addData)) {
            f("Red", wheelPositions.first)
            f("Green", wheelPositions.second)
            f("Blue", wheelPositions.third)

            f("X", robotPosition.x)
            f("Y", robotPosition.y)
            f("Rot", robotRotation)
        }

        // Draw robot for FTC Dashboard.
        packet.fieldOverlay().apply {
            setStroke("#3F51B5")
            setStrokeWidth(1)

            val pos = Vector2d(robotPosition.y, -robotPosition.x)

            // Swap X and Y because field coordinate is
            strokeCircle(pos.x, pos.y, this@EncoderLocalizationDemo.wheelRadius)

            val halfv = Rotation2d.exp(robotRotation).vec().times(0.5 * this@EncoderLocalizationDemo.wheelRadius)
            val p1 = pos + halfv
            val p2 = p1 + halfv

            strokeLine(p1.x, p1.y, p2.x, p2.y)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)

        telemetry.addData("Status", "Running")
    }

    private fun inverse(ticks: Triple<Double, Double, Double>): Twist2d {
        val rxy = Polar2d(RED_ANGLE - PI_2, ticks.first).toCartesian()
        val gxy = Polar2d(GREEN_ANGLE - PI_2, ticks.second).toCartesian()
        val bxy = Polar2d(BLUE_ANGLE - PI_2, ticks.third).toCartesian()

        return Twist2d(
            (rxy + gxy + bxy) / 1.5,
            (ticks.first + ticks.second + ticks.third) / this.wheelRadius / 3.0,
        )
    }
}
