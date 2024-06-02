//Code from 2023-2024 Botsburgh GitHub
// https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24


package org.firstinspires.ftc.teamcode.utils

import android.content.Context
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop
import kotlin.reflect.KProperty

/**
 * A set of functions that, when called, will reset the state of all APIs and components.
 */
private val resetFunctions = mutableSetOf<() -> Unit>()

/**
 * A listener that resets all [Resettable] variables each time an opmode begins initializing.
 *
 * You don't need to access this object manually. It is automatically called by the
 * [OpModeManagerNotifier] when the "Init" button is pressed, but before any other user-defined code
 * is ran. This means that APIs and opmodes can rely on their [Resettable] properties always being
 * reset.
 *
 * # History
 *
 * This API used to be called `Reset`. It acted like an API but you needed to call its
 * `Reset.init(this)` method first in opmode initialization or other APIs would break. It was
 * revised in [#46](https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24/pull/46) to automatically
 * run, so that nothing would break if someone forgot to write `Reset.init`.
 */
object ResetListener : OpModeManagerNotifier.Notifications {
    // An entrypoint when the app gets created. With it, we register a listener that calls
    // `resetAll()` before an opmode is initialized.
    @OnCreateEventLoop
    @JvmStatic
    fun register(
        @Suppress("UNUSED_PARAMETER") context: Context,
        ftcEventLoop: FtcEventLoop,
    ) {
        ftcEventLoop.opModeManager.registerListener(this)
    }

    override fun onOpModePreInit(opMode: OpMode) {
        this.resetAll()
    }

    override fun onOpModePreStart(opMode: OpMode) {}

    override fun onOpModePostStop(opMode: OpMode) {}

    private fun resetAll() {
        // Call each reset function
        resetFunctions.forEach { it() }
    }
}

/**
 * A property delegate that instruments resetting it between opmode runs.
 *
 * This is used in [delegate properties](https://kotlinlang.org/docs/delegated-properties.html). You
 * must pass a function when constructing the resettable property that returns its default value.
 *
 * ```
 * val property: Type by Resettable {
 *     // This is a function body.
 *     // You can run anything in here.
 *     val x = 1 + 2
 *
 *     // You do not need the `return` keyword.
 *     // The last expression in the block, will be returned.
 *     x + 2 // will return 5
 * }
 * ```
 *
 * # Example
 *
 * ```
 * object MyAPI : API() {
 *     // The starting value of someState is 10
 *     private var someState: Int by Resettable { 10 }
 * }
 * ```
 */
class Resettable<T>(private val default: () -> T) {
    /** The actual value of the resettable property. */
    private var inner: T = this.default()

    init {
        // Add the reset function to a global set.
        resetFunctions.add(this::reset)
    }

    // Simply return the inner value.
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        return this.inner
    }

    // Simply set the inner value.
    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        this.inner = value
    }

    /** A function that resets the value of [inner] to [default]'s return. */
    private fun reset() {
        this.inner = this.default()
    }
}