package org.firstinspires.ftc.teamcode.teleop.demos

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Rotation2d
import com.acmerobotics.roadrunner.Time
import com.acmerobotics.roadrunner.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiKinematics
import org.firstinspires.ftc.teamcode.utils.Polar2d

@TeleOp(name = "Encoder Localization Demo", group = "Demo")
@Disabled
class EncoderLocalizationDemo : OpMode() {
    private val driveSpeed = 0.3
    private val kinematics = KiwiKinematics(RobotConfig.KiwiLocalizer.RADIUS)

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
            KiwiKinematics.WheelTicks<Time>(
                DualNum.constant(TriWheels.red.currentPosition.toDouble() * RobotConfig.KiwiLocalizer.INCHES_PER_TICK, 1),
                DualNum.constant(TriWheels.green.currentPosition.toDouble() * RobotConfig.KiwiLocalizer.INCHES_PER_TICK, 1),
                DualNum.constant(TriWheels.blue.currentPosition.toDouble() * RobotConfig.KiwiLocalizer.INCHES_PER_TICK, 1),
            )

        // Find position of the entire robot.
        val (robotPosition, robotRotation) = this.kinematics.forward(wheelPositions).value()

        val packet = TelemetryPacket()

        // Log information to both FTC Dashboard and telemetry.
        for (f in listOf(packet::put, telemetry::addData)) {
            f("Red", wheelPositions.red)
            f("Green", wheelPositions.green)
            f("Blue", wheelPositions.blue)

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
            strokeCircle(pos.x, pos.y, RobotConfig.KiwiLocalizer.RADIUS)

            val halfv = Rotation2d.exp(robotRotation).vec().times(0.5 * RobotConfig.KiwiLocalizer.RADIUS)
            val p1 = pos + halfv
            val p2 = p1 + halfv

            strokeLine(p1.x, p1.y, p2.x, p2.y)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)

        telemetry.addData("Status", "Running")
    }
}
