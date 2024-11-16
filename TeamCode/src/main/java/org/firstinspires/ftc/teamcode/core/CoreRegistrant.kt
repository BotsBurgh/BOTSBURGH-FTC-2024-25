package org.firstinspires.ftc.teamcode.core

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop

object CoreRegistrant : OpModeManagerNotifier.Notifications {
    override fun onOpModePreInit(opMode: OpMode) {
        Resettable.resetAll()
        Logging.init(opMode)
    }

    override fun onOpModePreStart(opMode: OpMode) {
        Dependencies.checkDependencies()
    }

    override fun onOpModePostStop(opMode: OpMode) {
        Logging.close()
    }

    @OnCreateEventLoop
    @JvmStatic
    fun register(
        @Suppress("UNUSED_PARAMETER")
        context: Context,
        ftcEventLoop: FtcEventLoop,
    ) {
        ftcEventLoop.opModeManager.registerListener(this)
    }
}
