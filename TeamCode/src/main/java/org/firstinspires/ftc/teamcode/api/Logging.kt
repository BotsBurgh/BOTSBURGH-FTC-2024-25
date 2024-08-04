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
    lateinit var logFile: File
        private set
    lateinit var logWriter: BufferedWriter
        private set

    var fileHash = hashMapOf<String, BufferedWriter>()

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
     */
    fun createFile(fileName: String) {
        if (RobotConfig.debug) {


            fileHash[fileName] = BufferedWriter(FileWriter( File(BOTSBURGH_FOLDER, "/$fileName.csv"), true))
            File(BOTSBURGH_FOLDER, "/$fileName.csv").createNewFile()
        }
    }

    /**
     * Writes Double data to the targeted file
     */
    fun writeFile(file: String, data: Double) {
        if (RobotConfig.debug) {
            fileHash.get(file)?.write("$data, ")
            fileHash.get(file)?.write(opMode.runtime.toString())
            fileHash.get(file)?.newLine()
        }
    }

    /**
     * Writes Array Double data to the targeted file
     */
    fun writeFile(file: String, data: Array<Double>) {
        if (RobotConfig.debug) {
            for (i in data) fileHash.get(file)?.write("$i, ")
        }
        fileHash.get(file)?.write(opMode.runtime.toString())
        fileHash.get(file)?.newLine()
    }

    /**
     * Closes file
     **/
    fun close(file: String) {
        fileHash.get(file)?.close()
    }
}
