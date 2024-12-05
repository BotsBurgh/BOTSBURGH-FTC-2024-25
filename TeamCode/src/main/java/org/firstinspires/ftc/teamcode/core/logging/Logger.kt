package org.firstinspires.ftc.teamcode.core.logging

import android.util.Log
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.Logging.telemetryLog
import org.firstinspires.ftc.teamcode.core.Logging.writeToStream
import org.threeten.bp.Instant

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
