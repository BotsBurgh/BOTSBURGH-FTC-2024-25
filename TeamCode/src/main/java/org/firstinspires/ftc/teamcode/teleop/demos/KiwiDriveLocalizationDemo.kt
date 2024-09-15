package org.firstinspires.ftc.teamcode.teleop.demos

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiDrive
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiLocalizer
import org.firstinspires.ftc.teamcode.utils.Polar2d

@TeleOp(name = "Kiwi Drive Localization", group = "Demo")
@Disabled
class KiwiDriveLocalizationDemo : OpMode() {
    private val driveSpeed = 0.3

    override fun init() {
        TriWheels.init(this)
        KiwiLocalizer.init(this)
        Voltage.init(this)
        KiwiDrive.init(this)

        // Make wheels spin freely when moved, instead of resisting it.
        for (motor in TriWheels.wheels()) {
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }
    }

    override fun loop() {
        // Drive robot with left joystick, spin it with right joystick.
        TriWheels.drive(
            Polar2d.fromCartesian(gamepad1.left_stick_x.toDouble() * this.driveSpeed, -gamepad1.left_stick_y.toDouble() * this.driveSpeed),
            gamepad1.right_stick_x.toDouble(),
        )

        // Update our currently tracked position and rotation. This function does most of the
        // work in this opmode.
        KiwiDrive.updatePoseEstimates()

        val (pos, rotation) = KiwiDrive.pose

        val packet = TelemetryPacket()

        // Log information to both FTC Dashboard and telemetry.
        for (f in listOf(packet::put, telemetry::addData)) {
            f("X", pos.x)
            f("Y", pos.y)
            f("Rot", rotation.log())
        }

        // Draw robot for FTC Dashboard.
        packet.fieldOverlay().apply {
            setStroke("#3F51B5")
            setStrokeWidth(1)

            // Swap X and Y because field coordinate is
            strokeCircle(pos.x, pos.y, RobotConfig.KiwiLocalizer.RADIUS)

            val halfv = rotation.vec().times(0.5 * RobotConfig.KiwiLocalizer.RADIUS)
            val p1 = pos + halfv
            val p2 = p1 + halfv

            strokeLine(p1.x, p1.y, p2.x, p2.y)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)

        telemetry.addData("Status", "Running")
    }
}
