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
    private val log = this.logger(this)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        // TODO: Conditionally initialize this.
        FTCLogger.init(this.opMode)

        this.log.debug("Logging initialized.")
    }

    // TODO: Conditionally return `FTCLogger` or `UnitTestLogger`.
    fun logger(tag: String): Logger = FTCLogger(tag)

    /**
     * Creates a new [Logger] where the tag is the name of the class.
     */
    fun logger(clazz: Any): Logger = logger(clazz::class.simpleName ?: "Unknown")
}
