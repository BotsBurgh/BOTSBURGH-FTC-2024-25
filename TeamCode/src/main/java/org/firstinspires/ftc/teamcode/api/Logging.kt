package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import java.io.File
import java.io.FileWriter

object Logging: API() {

    override fun init(opMode: OpMode) {
        super.init(opMode)

//        if (RobotConfig.debug) {
//
//        }
        //@TODO delete old files
    }

    fun createFile(fileName: String) {

    }

}