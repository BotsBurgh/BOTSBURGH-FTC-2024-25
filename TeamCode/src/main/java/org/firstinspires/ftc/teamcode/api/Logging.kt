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
object Logging : API() {

    var fileHash = hashMapOf<String, BufferedWriter>()

    var volatileFileHash = hashMapOf<String, File>()

    override fun init(opMode: OpMode) {
        super.init(opMode)

        if (RobotConfig.debug) {
            for (file in BOTSBURGH_FOLDER.listFiles()) if (!file.isDirectory) file.delete()
        }
    }

    // root folder is /storage/emulated/0/BotsBurgh
    private val BOTSBURGH_FOLDER: File by lazy {
        val res = File(AppUtil.ROOT_FOLDER, "/BotsBurgh/")
        res.mkdirs()
        res
    }

    /**
     * Creates new files to log to (old files are deleted after every run). File must be closed.
     */
    fun createFile(fileName: String) {
        if (RobotConfig.debug) {
            fileHash[fileName] = BufferedWriter(FileWriter(File(BOTSBURGH_FOLDER, "/$fileName.csv"), true))
            File(BOTSBURGH_FOLDER, "/$fileName.csv").createNewFile()
        }
    }

    /**
     * Creates new volatile files to log to (old files are deleted after every run). Only use volatile files to diagnose power issues.
     */
    fun createVolatileFile(fileName: String) {
        if (RobotConfig.debug) {
            volatileFileHash[fileName] = File(BOTSBURGH_FOLDER, "/$fileName.csv")
            File(BOTSBURGH_FOLDER, "/$fileName.csv").createNewFile()
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
        if (RobotConfig.debug) {
            fileHash[file]!!.write("$data, ")
            fileHash[file]!!.write(opMode.runtime.toString())
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
        if (RobotConfig.debug) {
            for (i in data) fileHash[file]!!.write("$i, ")
        }
        fileHash[file]!!.write(opMode.runtime.toString())
        fileHash[file]!!.newLine()
    }

    /**
     * Writes Double data to the targeted volatile file
     * @param file Name of the file that is being logged to
     * @param data Double data that is being logged
     */
    fun writeVolatileFile(
        file: String,
        data: Double,
    ) {
        if (RobotConfig.debug) {
            fileHash[file]!!.write("$data, ")
            fileHash[file]!!.write(opMode.runtime.toString())
            fileHash[file]!!.newLine()
        }
    }

    /**
     * Writes Array Double data to the targeted volatile file
     * @param file Name of the file that is being logged to
     * @param data Array Double data that is being logged
     */

    fun writeVolatileFile(
        file: String,
        data: Array<Double>,
    ) {
        if (RobotConfig.debug) {
            for (i in data) fileHash[file]!!.write("$i, ")
        }
        fileHash[file]!!.write(opMode.runtime.toString())
        fileHash[file]!!.newLine()
    }

        /**
     * Closes file. Only use on non-volatile files
     * @param file Name of file to close
     **/
    fun close(file: String) {
        fileHash[file]!!.close()
    }
}
