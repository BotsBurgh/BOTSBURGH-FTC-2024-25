package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.core.API
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object Logging: API() {

    lateinit var logFile: File
        private set
    lateinit var logWriter: BufferedWriter
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)


//        if (RobotConfig.debug) {
//
//        }
        //@TODO delete old files
    }

    val BOTSBURGH_FOLDER: File by lazy {
        val res = File(AppUtil.ROOT_FOLDER, "/BotsBurgh/")
        res.mkdirs()
        res
    }

    /**
     *
     */
    fun createFile(fileName: String) {
        logFile = File(BOTSBURGH_FOLDER, "/$fileName.csv")
        logWriter = BufferedWriter(FileWriter(logFile))
        logFile.createNewFile()
    }

    fun writeFile(data: Double) {
        logWriter.write("$data")
        logWriter.write(opMode.runtime.toString())
        logWriter.newLine()
    }

    fun writeFile(data: Array<Double>) {
        for (i in data) logWriter.write(i.toString())

        logWriter.write(opMode.runtime.toString())
        logWriter.newLine()
    }


}