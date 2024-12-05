package org.firstinspires.ftc.teamcode.core

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.logging.Logger
import org.threeten.bp.Instant
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream

/**
 * An API for logging information to the telemetry, a compressed log file, and Android's standard
 * logger.
 *
 * This API is automatically initialized by [CoreRegistrant].
 */
object Logging : API() {
    private lateinit var compressedFile: File
    private lateinit var compressedStream: OutputStream

    internal lateinit var telemetryLog: Telemetry.Log

    private val log = Logger(this)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        // Create the log folder, if it does not exist already.
        RobotConfig.Logging.LOG_FOLDER.mkdirs()

        // Create compressed file.
        compressedFile = File(RobotConfig.Logging.LOG_FOLDER, "${Instant.now()}.txt.gz")
        compressedFile.createNewFile()

        // Create stream for compressed file.
        compressedStream = GZIPOutputStream(FileOutputStream(compressedFile)).buffered()

        // Configure telemetry log.
        telemetryLog = this.opMode.telemetry.log()
        telemetryLog.capacity = RobotConfig.Logging.TELEMETRY_CAPACITY
        telemetryLog.displayOrder = RobotConfig.Logging.TELEMETRY_ORDER

        this.log.debug("Logging initialized.")
    }

    /**
     * Flushes any buffered text, writing it to the log file.
     *
     * Logged text is not guaranteed to be written to the [compressedStream] until the end of the
     * opmode, when [close] is called. If the program crashes, though, the logged messages may be
     * lost. This method lets you force all logged messages to be written to the file, at the cost
     * of a small performance hit.
     *
     * Call this after information critical to diagnosing an issue has been logged, but else try to
     * avoid it.
     *
     * # Example
     *
     * ```
     * logger.warn("Important information!")
     * // Ensure this information is saved.
     * StringLogging.flush()
     * ```
     */
    fun flush() {
        compressedStream.flush()
        this.opMode.telemetry.update()
    }

    /**
     * Flush the buffer and close the file stream.
     *
     * This is automatically called when an opmode exits by [CoreRegistrant]. Text logged after this
     * is called will not be recorded.
     */
    fun close() {
        this.log.debug("Closing log file.")
        compressedStream.close()
    }

    /**
     * Utility method that writes a piece of text to the [compressedStream].
     *
     * Note that this does not formatting beyond adding a newline to the end of the message.
     *
     * # Example
     *
     * ```
     * StringLogging.writeToStream("${Instant.now()} My cool message!\n")
     * ```
     */
    internal fun writeToStream(text: String) = compressedStream.write((text + '\n').toByteArray())
}
