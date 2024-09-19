package org.firstinspires.ftc.teamcode.teleop.tuning

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.MotorFeedforward
import com.acmerobotics.roadrunner.TimeProfile
import com.acmerobotics.roadrunner.constantProfile
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.Voltage
import org.firstinspires.ftc.teamcode.api.roadrunner.KiwiLocalizer

@TeleOp(name = "Feedforward Tuner", group = "Tuner")
class FeedforwardTuner : LinearOpMode() {
    /** Distance in inches the robot will travel. */
    private val distance = 64.0

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().telemetry)

        Voltage.init(this)
        TriWheels.init(this)

        val profile =
            TimeProfile(
                constantProfile(
                    this.distance,
                    0.0,
                    RobotConfig.KiwiDrive.MAX_WHEEL_VEL,
                    RobotConfig.KiwiDrive.MIN_PROFILE_ACCEL,
                    RobotConfig.KiwiDrive.MAX_PROFILE_ACCEL,
                ).baseProfile,
            )

        telemetry.apply {
            addData("Status", "Initialized")
            update()
        }

        val encoders = mapOf(
            "vRed" to KiwiLocalizer.red,
            "vGreen" to KiwiLocalizer.green,
            "vBlue" to KiwiLocalizer.blue,
        )

        waitForStart()

        var movingForward = true
        var startTs = System.nanoTime() / 1e9

        while (!isStopRequested) {
            telemetry.addData("Status", "Running")

            for ((name, encoder) in encoders) {
                val v = encoder.getPositionAndVelocity().velocity
                telemetry.addData(name, v)
            }

            val ts = System.nanoTime() / 1e9
            val t = ts - startTs

            if (t > profile.duration) {
                movingForward = !movingForward
                startTs = ts
            }

            var v = profile[t].drop(1)

            if (!movingForward) {
                v = v.unaryMinus()
            }

            telemetry.addData("vref", v[0])

            val power = MotorFeedforward(RobotConfig.KiwiDrive.K_S, RobotConfig.KiwiDrive.K_V, RobotConfig.KiwiDrive.K_A).compute(v) / Voltage.get()

            TriWheels.drive(0.0, power)

            telemetry.update()
        }
    }
}
