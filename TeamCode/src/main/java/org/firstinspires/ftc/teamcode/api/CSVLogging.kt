package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * An API for creating, managing, and writing to logging files.
 */
object CSVLogging : API() {
    private val fileHash = hashMapOf<String, BufferedWriter>()

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
     * Creates new files to log to (old files are deleted after every run).
     * File must be closed.
     */
    fun createFile(fileName: String) = if (RobotConfig.debug) {
            fileHash[fileName] =
                BufferedWriter(FileWriter(File(BOTSBURGH_FOLDER, "/$fileName.csv"), true))
            File(BOTSBURGH_FOLDER, "/$fileName.csv").createNewFile()
        }
    }

    /**
     * Writes Double data to the targeted file
     * @param file Name of the file that is being logged to
     * @param data Double data that is being logged
     * @param flush Set to false by default. Set to true to flush the file writer.
     */
    fun writeFile(
        file: String,
        data: Double,
        flush: Boolean = false,
    ) {
        if (RobotConfig.debug) {
            fileHash[file]!!.write("$data, ")
            fileHash[file]!!.write(opMode.runtime.toString())
            fileHash[file]!!.newLine()

            if (flush) {
                fileHash[file]!!.flush()
            }
        }
    }

    /**
     * Writes Array Double data to the targeted file
     * @param file Name of the file that is being logged to
     * @param data Array Double data that is being logged
     * @param flush Set to false by default. Set to true to flush the file writer.
     */
    fun writeFile(
        file: String,
        data: Array<Double>,
        flush: Boolean = false,
    ) {
        if (RobotConfig.debug) {
            for (i in data) fileHash[file]!!.write("$i, ")
        }
        fileHash[file]!!.write(opMode.runtime.toString())
        fileHash[file]!!.newLine()

        if (flush) {
            fileHash[file]!!.flush()
        }
    }

    /**
     * Closes all open files
     * @param file Name of file to close
     */
    fun close() {
        for ((file, stream) in fileHash) {
            try {
                stream.close()
            } catch (e: IOException) {
                throw IllegalArgumentException("The file $file does not exist")
            }
        }
    }
}