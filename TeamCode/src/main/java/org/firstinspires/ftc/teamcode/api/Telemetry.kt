package org.firstinspires.ftc.teamcode.api

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * An API for handling telemetry feedback
 * provides methods for logging + add new details from last year
 */
object Telemetry: API() {
    override fun init(opMode: OpMode) {
        super.init(opMode)

        if (RobotConfig.debug) {
            // Makes opMode.telemetry calls send to both driver station and FTC Dashboard
            opMode.telemetry =
                MultipleTelemetry(
                    opMode.telemetry,
                    FtcDashboard.getInstance().telemetry,
                )
        }
    }
    /** This additionally logs the current [RobotConfig]. */
    fun sayInitialized() =
            with(opMode.telemetry) {
                addData("Status", "Initialized")

                // Log the current robot configuration
                addData("Config", RobotConfig.toString())

                update()
            }

    fun sayRunning() =
        with(opMode.telemetry) {
            addData("Status", "Running")
            update()
        }

    fun sayStarted() =
        with(opMode.telemetry) {
            addData("Status", "Started")
            update()
        }
    }


