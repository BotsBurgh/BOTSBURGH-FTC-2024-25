package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.API
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * An API for creating, managing, and writing to logging files.
 */
object StringLogging : API() {
    private val fileHash = hashMapOf<String, BufferedWriter>()

    override fun init(opMode: OpMode) {
        super.init(opMode)

        // Create the folder, if it does not exist already.
        RobotConfig.Logging.BOTSBURGH_FOLDER.mkdirs()

        if (RobotConfig.DEBUG) {
            for (file in RobotConfig.Logging.BOTSBURGH_FOLDER.listFiles()!!) if (!file.isDirectory) file.delete()
        }
    }

    /**
     * Creates new files to log to (old files are deleted after every run). File must be closed.
     */
    fun createFile(fileName: String) {
        if (RobotConfig.DEBUG) {
            fileHash[fileName] = BufferedWriter(FileWriter(File(RobotConfig.Logging.BOTSBURGH_FOLDER, "/$fileName.log"), true))
        }
    }

    /**
     * Writes Double data to the targeted file
     * @param file Name of the file that is being logged to
     * @param data data that is being logged
     */
    fun writeFile(
        file: String,
        data: Any,
    ) {
        if (RobotConfig.DEBUG) {
            val writer = this.fileHash[file]!!

            writer.write("${opMode.runtime}, ")
            writer.write(data.toString())
            writer.newLine()
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
