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

/**
 * An opmode used to tune the kS, kV, and kA values of [RobotConfig.KiwiDrive].
 *
 * This is adapted from [ManualFeedforwardTuner](https://github.com/acmerobotics/road-runner-ftc/blob/c9f0be75158276c5dfcd82ccabb639a15a200f98/RoadRunner/src/main/java/com/acmerobotics/roadrunner/ftc/Tuning.kt#L465).
 */
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

        val encoders =
            mapOf(
                "vRed" to KiwiLocalizer.red,
                "vGreen" to KiwiLocalizer.green,
                "vBlue" to KiwiLocalizer.blue,
            )

        telemetry.apply {
            addData("Status", "Initialized")
            update()
        }

        waitForStart()

        var movingForward = true
        var startTs = System.nanoTime() / 1e9

        // We avoid `opModeIsActive()` because it calls `Thread.yield()`.
        while (!isStopRequested) {
            telemetry.addData("Status", "Running")

            for ((name, encoder) in encoders) {
                val v = encoder.getPositionAndVelocity().velocity
                telemetry.addData(name, v)
            }

            val currentTs = System.nanoTime() / 1e9
            val deltaTs = currentTs - startTs

            if (deltaTs > profile.duration) {
                movingForward = !movingForward
                startTs = currentTs
            }

            var v = profile[deltaTs].drop(1)

            if (!movingForward) {
                v = v.unaryMinus()
            }

            val power = MotorFeedforward(RobotConfig.KiwiDrive.K_S, RobotConfig.KiwiDrive.K_V, RobotConfig.KiwiDrive.K_A).compute(v) / Voltage.get()

            TriWheels.drive(0.0, power)

            telemetry.apply {
                addData("vRef", v[0])
                update()
            }
        }
    }
}
