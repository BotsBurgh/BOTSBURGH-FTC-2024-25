package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.API
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * An API for creating, managing, and writing to logging files.
 */
object CsvLogging : API() {
    // Accessible at `/sdcard/BotsBurgh`.
    private val BOTSBURGH_FOLDER = File(AppUtil.ROOT_FOLDER, "/BotsBurgh")

    private val fileHash = hashMapOf<String, BufferedWriter>()

    override fun init(opMode: OpMode) {
        super.init(opMode)

        // Create the folder, if it does not exist already.
        this.BOTSBURGH_FOLDER.mkdirs()

        if (RobotConfig.DEBUG) {
            for (file in BOTSBURGH_FOLDER.listFiles()!!) if (!file.isDirectory) file.delete()
        }
    }

    /**
     * Creates new files to log to (old files are deleted after every run). File must be closed.
     */
    fun createFile(fileName: String) {
        if (RobotConfig.DEBUG) {
            fileHash[fileName] = BufferedWriter(FileWriter(File(BOTSBURGH_FOLDER, "/$fileName.csv"), true))
        }
    }

    /**
     * Writes Double data to the targeted file
     * @param file Name of the file that is being logged to
     * @param data Double data that is being logged
     */
    fun writeFile(
        file: String,
        data: Double,
    ) {
        if (RobotConfig.DEBUG) {
            fileHash[file]!!.write("${opMode.runtime}, ")
            fileHash[file]!!.write(data.toString())
            fileHash[file]!!.newLine()
        }
    }

    /**
     * Writes Array Double data to the targeted file
     * @param file Name of the file that is being logged to
     * @param data Array Double data that is being logged
     */
    fun writeFile(
        file: String,
        data: Array<Double>,
    ) {
        if (RobotConfig.DEBUG) {
            fileHash[file]!!.write("${opMode.runtime}")
            for (i in data) fileHash[file]!!.write(", $i")
            fileHash[file]!!.newLine()
        }
    }

    fun flush(file: String) {
        this.fileHash[file]!!.flush()
    }

    /**
     * Closes all open files.
     */
    fun close() {
        for (writer in this.fileHash.values) {
            writer.close()
        }
    }
}
