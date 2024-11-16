package org.firstinspires.ftc.teamcode.core

import android.content.Context
import android.util.Log
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.RobotConfig
import org.threeten.bp.Instant
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream
import kotlin.reflect.KClass

/**
 * An API for logging information to the telemetry, a compressed log file, and Android's standard
 * logger.
 *
 * This API is automatically initialized by [LoggingRegistrant].
 */
object Logging : API() {
    private lateinit var compressedFile: File
    private lateinit var compressedStream: OutputStream

    private lateinit var telemetryLog: Telemetry.Log

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
    }

    /**
     * Flushes any buffered text, writing it to the log file.
     *
     * Because repeatedly writing to a file can be expensive, this APIs stores text in a buffer and
     * writes it all at once after a certain amount of bytes has been logged. As a side-effect some
     * text may not be written when a program exits. Consider calling [flush] frequently to avoid
     * data loss.
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
     * You should call this method before a program exits. Text logged after the fact will not be
     * recorded.
     */
    fun close() = compressedStream.close()

    /**
     * Utility method that writes a piece of text to the [compressedStream].
     *
     * Note that this does not formatting beyond adding a newline to the end of the message.
     *
     * # Example
     *
     * ```
     * StringLogging.write("${Instant.now()} My cool message!\n")
     * ```
     */
    private fun write(text: String) = compressedStream.write((text + '\n').toByteArray())

    /**
     * A logger tagged to a specific API.
     *
     * # Example
     *
     * ```
     * object MyAPI : API() {
     *     // Tag is the name of the class, in this case "MyAPI".
     *     val log = Logging.Logger(this::class)
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
        constructor(clazz: KClass<*>) : this(clazz.simpleName ?: "Unknown")

        fun debug(msg: Any) {
            val msg = msg.toString()
            val formattedMessage = "[${Instant.now()} DEBUG ${this.tag}] $msg"

            Log.d(this.tag, msg)
            write(formattedMessage)

            if (RobotConfig.Logging.FILTER_LEVEL.ordinal <= Level.Debug.ordinal) {
                telemetryLog.add(formattedMessage)
            }
        }

        fun info(msg: Any) {
            val msg = msg.toString()
            val formattedMessage = "[${Instant.now()} INFO ${this.tag}] $msg"

            Log.i(this.tag, msg)
            write(formattedMessage)

            if (RobotConfig.Logging.FILTER_LEVEL.ordinal <= Level.Info.ordinal) {
                telemetryLog.add(formattedMessage)
            }
        }

        fun warn(msg: Any) {
            val msg = msg.toString()
            val formattedMessage = "[${Instant.now()} WARN ${this.tag}] $msg"

            Log.w(this.tag, msg)
            write(formattedMessage)

            if (RobotConfig.Logging.FILTER_LEVEL.ordinal <= Level.Warn.ordinal) {
                telemetryLog.add(formattedMessage)
            }
        }

        fun error(msg: Any) {
            val msg = msg.toString()
            val formattedMessage = "[${Instant.now()} ERROR ${this.tag}] $msg"

            Log.e(this.tag, msg)
            write(formattedMessage)

            if (RobotConfig.Logging.FILTER_LEVEL.ordinal <= Level.Error.ordinal) {
                telemetryLog.add(formattedMessage)
            }
        }

        /** See [Logging.flush] for what this does. */
        fun flush() = Logging.flush()
    }

    enum class Level {
        Debug,
        Info,
        Warn,
        Error,
    }
}
