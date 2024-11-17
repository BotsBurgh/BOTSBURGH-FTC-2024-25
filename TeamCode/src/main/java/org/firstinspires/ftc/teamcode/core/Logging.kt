package org.firstinspires.ftc.teamcode.core

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.RobotConfig
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

    private lateinit var telemetryLog: Telemetry.Log

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
    private fun writeToStream(text: String) = compressedStream.write((text + '\n').toByteArray())

    /**
     * A logger tagged to a specific API.
     *
     * # Example
     *
     * ```
     * object MyAPI : API() {
     *     // Tag is the name of the class, in this case "MyAPI".
     *     val log = Logging.Logger(this)
     *
     *     // Alternatively, specify the tag yourself.
     *     val log = Logging.Logger("MyTag")
     *
     *     fun myMethod() {
     *         this.log.warning("Be careful!")
     *         // ...
     *     }
     * }
     * ```
     */
    class Logger(private val tag: String) {
        constructor(clazz: Any) : this(clazz::class.simpleName ?: "Unknown")

        fun debug(msg: Any) = this.log(Level.Debug, msg.toString())

        fun info(msg: Any) = this.log(Level.Info, msg.toString())

        fun warn(msg: Any) = this.log(Level.Warn, msg.toString())

        fun error(msg: Any) = this.log(Level.Error, msg.toString())

        private fun log(
            level: Level,
            msg: String,
        ) {
            val formattedMessage = "[${Instant.now()} ${level.name} ${this.tag}] $msg"

            // Log message to Android's logger, accessible through Android Studio.
            Log.println(level.priority, this.tag, msg)

            // Write formatted message to the compressed log file.
            writeToStream(formattedMessage)

            // Log the message to the driver station if its level is not filtered out. (For example,
            // debug (priority of 3) messages are skipped when the filter is set to warn (priority
            // of 5), but warn and error (priority of 6) messages are still displayed.
            if (RobotConfig.Logging.FILTER_LEVEL.priority <= level.priority) {
                telemetryLog.add(formattedMessage)
            }
        }
    }

    enum class Level(val priority: Int) {
        Debug(Log.DEBUG),
        Info(Log.INFO),
        Warn(Log.WARN),
        Error(Log.ERROR),
    }
}
