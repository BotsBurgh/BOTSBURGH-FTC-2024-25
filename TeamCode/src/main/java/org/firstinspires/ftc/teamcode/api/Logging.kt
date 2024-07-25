package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * An API for creating, managing, and writing to logging files.
 */
object Logging: API() {

    lateinit var logFile: File
        private set
    lateinit var logWriter: BufferedWriter
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        if (RobotConfig.debug) {
            //TODO delete old files
        }

    }

    private val BOTSBURGH_FOLDER: File by lazy {
        val res = File(AppUtil.ROOT_FOLDER, "/BotsBurgh/")
        res.mkdirs()
        res
    }

    /**
     * Creates new files to log to (old files are deleted after every run)
     */
    fun createFile(fileName: String) {
        if (RobotConfig.debug) {
            logFile = File(BOTSBURGH_FOLDER, "/$fileName.csv")
            logWriter = BufferedWriter(FileWriter(logFile, true))
            logFile.createNewFile()
        }
    }

    /**
     * Writes Double data to the targeted file
     */
    fun writeFile(data: Double) {
        if (RobotConfig.debug) {
            logWriter.write("$data")
            logWriter.write(opMode.runtime.toString())
            logWriter.newLine()
        }
    }

    /**
     * Writes Array Double data to the targeted file
     */
    fun writeFile(data: Array<Double>) {
        if (RobotConfig.debug) {
            for (i in data) logWriter.write("$i")

            logWriter.write(opMode.runtime.toString())
            logWriter.newLine()
        }
    }

    /**
     * Closes file
     **/
    fun close() {
        logWriter.close()
    }
}