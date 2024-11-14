package org.firstinspires.ftc.teamcode.api

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.API
import org.threeten.bp.Instant
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream
import kotlin.reflect.KClass

/**
 * An API for creating, managing, and writing to logging files.
 */
object Logging : API() {
    private lateinit var currentFile: File
    private lateinit var compressedStream: OutputStream

    override fun init(opMode: OpMode) {
        super.init(opMode)

        // Create the log folder, if it does not exist already.
        RobotConfig.Logging.LOG_FOLDER.mkdirs()

        this.currentFile = File(RobotConfig.Logging.LOG_FOLDER, "${Instant.now()}.txt.gz")
        this.currentFile.createNewFile()

        this.compressedStream = GZIPOutputStream(FileOutputStream(this.currentFile)).buffered()
    }

    /**
     * Utility method that writes a piece of text to the [compressedStream].
     *
     * Note that this does not formatting whatsoever. You will need to manually insert newlines
     * `\n`, and add the time and log levels yourself.
     *
     * # Example
     *
     * ```
     * StringLogging.write("${Instant.now()} My cool message!\n")
     * ```
     */
    private fun write(text: String) {
        this.compressedStream.write(text.toByteArray())
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
    fun flush() = this.compressedStream.flush()

    /**
     * Flush the buffer and close the file stream.
     *
     * You should call this method before a program exits. Text logged after the fact will not be
     * recorded.
     */
    fun close() = this.compressedStream.close()

    class Logger(private val tag: String) {
        constructor(clazz: KClass<*>) : this(clazz.simpleName ?: "Unknown")

        fun debug(msg: Any) {
            val msg = msg.toString()
            Log.d(this.tag, msg)
            write("[${Instant.now()} DEBUG ${this.tag}] $msg")
        }

        fun info(msg: Any) {
            Log.i(this.tag, msg.toString())
            write("[${Instant.now()} INFO ${this.tag}] $msg")
        }

        fun warn(msg: Any) {
            Log.w(this.tag, msg.toString())
            write("[${Instant.now()} WARN ${this.tag}] $msg")
        }

        fun error(msg: Any) {
            Log.e(this.tag, msg.toString())
            write("[${Instant.now()} ERROR ${this.tag}] $msg")
        }
    }
}
