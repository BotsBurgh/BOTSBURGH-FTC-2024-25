package org.firstinspires.ftc.teamcode.core

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop

object CoreRegistrant : OpModeManagerNotifier.Notifications {
    private val log = Logging.Logger(this)

    override fun onOpModePreInit(opMode: OpMode) {
        Resettable.resetAll()

        if (opMode !is OpModeManagerImpl.DefaultOpMode) {
            Logging.init(opMode)
        }
    }

    override fun onOpModePreStart(opMode: OpMode) {
        if (opMode !is OpModeManagerImpl.DefaultOpMode) {
            Dependencies.checkDependencies()
        }
    }

    override fun onOpModePostStop(opMode: OpMode) {
        if (opMode !is OpModeManagerImpl.DefaultOpMode) {
            Logging.close()
        }
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
