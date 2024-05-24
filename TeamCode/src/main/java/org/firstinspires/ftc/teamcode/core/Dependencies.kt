package org.firstinspires.ftc.teamcode.core

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop

object Dependencies : OpModeManagerNotifier.Notifications {
    private val initializedAPIs = mutableSetOf<API>()

    override fun onOpModePreInit(opMode: OpMode) {
        this.initializedAPIs.clear()
    }

    override fun onOpModePreStart(opMode: OpMode) {
        this.checkDependencies()
    }

    override fun onOpModePostStop(opMode: OpMode) {}

    @OnCreateEventLoop
    @JvmStatic
    fun register(
        @Suppress("UNUSED_PARAMETER")
        context: Context,
        ftcEventLoop: FtcEventLoop,
    ) {
        ftcEventLoop.opModeManager.registerListener(this)
    }

    internal fun registerAPI(api: API) {
        this.initializedAPIs.add(api)
    }

    private fun checkDependencies() {
        for (api in this.initializedAPIs) {
            for (dep in api.dependencies) {
                if (!this.initializedAPIs.contains(dep)) {
                    throw MissingDependency(api::class, dep::class, this.initializedAPIs)
                }
            }
        }
    }
}
