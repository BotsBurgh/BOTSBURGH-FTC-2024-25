package org.firstinspires.ftc.teamcode.teleop.tuning

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiDrive
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiLocalizer

@TeleOp(name = "Localizer Test", group = GROUP)
class CLocalizerTest : OpMode() {
    val t = MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().telemetry)

    override fun init() {
        TriWheels.init(this)
        KiwiLocalizer.init(this)
        Voltage.init(this)
        KiwiDrive.init(this)

        // Make the wheels float so that they spin freely. They're being pushed!
        for (wheel in TriWheels.wheels()) {
            wheel.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }

        this.t.apply {
            addData("Status", "Initialized")
            update()
        }
    }

    override fun loop() {
        KiwiDrive.updatePoseEstimates()

        this.t.apply {
            addData("Status", "Running")
            addData("X", KiwiDrive.pose.position.x)
            addData("Y", KiwiDrive.pose.position.y)
            addData("Angle", KiwiDrive.pose.heading.toDouble())
            update()
        }
    }
}
