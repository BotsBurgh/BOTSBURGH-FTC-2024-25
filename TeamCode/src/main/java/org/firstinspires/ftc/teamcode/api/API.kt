//Code from 2023-2024 Botsburgh GitHub
// https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24

package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.utils.APIDependencies
import org.firstinspires.ftc.teamcode.utils.Resettable

/**
 * An API is shared code used by opmodes.
 *
 * An API needs to be initialized with the [init] function before it can be used. In general, APIs
 * should not have side-effects unless one of their functions is called. [init] should only be
 * called once per opmode.
 *
 * If an API requires methods only provided by a [LinearOpMode], it must override [isLinear] and set
 * it to `true`. It can then access the [linearOpMode] property, which is like [opMode] but casted
 * to the [LinearOpMode] type.
 *
 * An API can specify that it depends on another API by overriding the [dependencies] function and
 * returning a [Set] of all the APIs it requires. These dependencies will be checked at runtime.
 * It is additionally recommended to document in the comments what APIs your API depends on.
 */
abstract class API {
    private var uninitializedOpMode: OpMode? by Resettable { null }

    /**
     * Used to access the opmode provided in [init].
     *
     * @throws NullPointerException If the API has not been initialized before use.
     */
    protected val opMode: OpMode
        get() =
            this.uninitializedOpMode
                ?: throw NullPointerException("API ${this::class.simpleName} has not been initialized with the OpMode before being used.")

    /**
     * Equivalent to [opMode] but automatically casted to be a [LinearOpMode].
     *
     * @throws RuntimeException If [isLinear] is not set to true.
     */
    protected val linearOpMode: LinearOpMode
        get() {
            if (!this.isLinear) {
                throw RuntimeException(
                    """
                    API ${this::class.simpleName} tried to access linearOpMode without setting isLinear.
                    Please override isLinear to true. (Currently set to ${this.isLinear}.)
                    """.trimIndent(),
                )
            }

            return this.opMode as LinearOpMode
        }

    /**
     * Specifies whether this API requires a [LinearOpMode] to function.
     *
     * When true, [init] will throw an exception if it is not passed a [LinearOpMode]. Additionally,
     * the API will be able to access the [linearOpMode] property without an error being thrown.
     */
    open val isLinear: Boolean = false

    /**
     * Initializes the API to use the given [opMode].
     *
     * This must be called before any other function provided by the API.
     *
     * ## Note to Implementors
     *
     * Make sure to call `super.init(opMode)` at the beginning of the overloaded function, or the
     * API will not properly store a reference to the op-mode.
     *
     * @throws IllegalStateException If called more than once.
     * @throws IllegalArgumentException If API requires a [LinearOpMode] but one was not passed in.
     */
    open fun init(opMode: OpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (this.uninitializedOpMode != null) {
            val apiName = this::class.simpleName

            throw IllegalStateException("Tried to initialize API $apiName more than once.")
        }

        // If this API requires LinearOpMode, but only a regular OpMode was passed.
        if (this.isLinear && opMode !is LinearOpMode) {
            val apiName = this::class.simpleName

            throw IllegalArgumentException(
                """
                Tried to initialized linear API $apiName without a LinearOpMode.
                Please make sure that your opmode extends LinearOpMode.
                Also please check that the API has not accidentally set isLinear to true.
                """.trimIndent(),
            )
        }

        // Save the opmode so it can be used by the API.
        this.uninitializedOpMode = opMode

        // Register this API is initialized, so dependencies can be checked.
        APIDependencies.registerAPI(this)
    }

    /**
     * A function that returns a [Set] of all other APIs it depends on to function.
     *
     * By default it returns an [emptySet], assuming that the API does not have any dependencies.
     *
     * # Example
     *
     * ```
     * object Wheels : API() {
     *     fun drive(fl: Double, fr: Double, bl: Double, br: Double)
     * }
     *
     * object SuperSlickMovement : API() {
     *     fun spin(power: Double) {
     *         Wheels.drive(power, power, power, power)
     *     }
     *
     *     // SuperSliceMovement needs Wheels in order to work.
     *     override fun dependencies() = setOf(Wheels)
     * }
     * ```
     */
    open fun dependencies(): Set<API> = emptySet()
}