//Code from 2023-2024 Botsburgh GitHub
// https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24


package org.firstinspires.ftc.teamcode.utils

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
import org.firstinspires.ftc.teamcode.api.API

/**
 * An object that orchestrates API dependency checking.
 */
object APIDependencies : OpModeManagerNotifier.Notifications {
    /** A list of all APIs initialized for the current opmode, populated when "Init" is pressed. */
    private val initializedAPIs = mutableSetOf<API>()

    @OnCreateEventLoop
    @JvmStatic
    fun register(
        @Suppress("UNUSED_PARAMETER") context: Context,
        ftcEventLoop: FtcEventLoop,
    ) {
        ftcEventLoop.opModeManager.registerListener(this)
    }

    override fun onOpModePreInit(opMode: OpMode) {
        // Clear initialized API list when a new opmode is selected.
        initializedAPIs.clear()
    }

    // Checks dependencies when the start button is pressed.
    // It will only do this if debug mode is active, so as not to waste time during competitions.
    override fun onOpModePreStart(opMode: OpMode) {
        if (RobotConfig.debug) {
            checkDependencies()
        }
    }

    override fun onOpModePostStop(opMode: OpMode) {
    }

    /**
     * Registers that an API has been initialized.
     *
     * You should never call this method manually. It is automatically done when `API.init` is
     * called. (Which could also look like `super.init(opMode)`.)
     */
    internal fun registerAPI(api: API) {
        initializedAPIs.add(api)
    }

    /**
     * Checks that the dependencies of initialized APIs are also initialized.
     *
     * @throws RuntimeException If a dependency is not initialized.
     */
    private fun checkDependencies() {
        // Go through every single API dependency and check if it is in the list.
        for (api in initializedAPIs) {
            for (dep in api.dependencies()) {
                if (!initializedAPIs.contains(dep)) {
                    // Report the error with as much information as possible to debug the issue.
                    val apiName = api::class.simpleName
                    val depName = dep::class.simpleName

                    throw RuntimeException(
                        """
                        API $apiName requires dependency $depName, but it was not initialized.
                        Please call $depName.init(this) in the initialization phase of the opmode.
                        Initialized APIs: $initializedAPIs.
                        """.trimIndent(),
                    )
                }
            }
        }
    }
}