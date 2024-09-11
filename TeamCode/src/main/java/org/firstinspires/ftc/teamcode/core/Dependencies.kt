package org.firstinspires.ftc.teamcode.core

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop

/**
 * A listener that ensures API dependencies are properly initialized when the start button is
 * pressed.
 */
object Dependencies : OpModeManagerNotifier.Notifications {
    private val initializedAPIs: MutableSet<API> by Resettable { mutableSetOf() }

    override fun onOpModePreInit(opMode: OpMode) {}

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

    /**
     * Registers an API as initialized.
     *
     * This is called within the super implementation of [API.init], and should never be called
     * manually.
     */
    internal fun registerAPI(api: API) {
        this.initializedAPIs.add(api)
    }
    internal fun checkDependencies() {
        for (api in this.initializedAPIs) {
            for (dep in api.dependencies) {
                if (!this.initializedAPIs.contains(dep)) {
                    throw MissingDependency(api::class, dep::class, this.initializedAPIs)
                }
            }
        }
    }
}
