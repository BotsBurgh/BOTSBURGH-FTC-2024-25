package org.firstinspires.ftc.teamcode.core.logging

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.core.CoreRegistrant

/**
 * An API for logging information to the telemetry, a compressed log file, and Android's standard
 * logger.
 *
 * This API is automatically initialized by [CoreRegistrant].
 */
object Logging : API() {
    private val log = FTCLogger(this)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        FTCLogger.init(this.opMode)

        this.log.debug("Logging initialized.")
    }
}
