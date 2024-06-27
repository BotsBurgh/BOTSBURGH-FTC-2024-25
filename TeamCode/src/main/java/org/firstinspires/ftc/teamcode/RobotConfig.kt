// We are treating config fields as constant even through they may be changed, which is why we use
// SCREAMING_SNAKE_CASE for them instead of `ktlint`'s desired lowerCamelCase.
@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.config.Config
import kotlin.math.PI

/**
 * This is an immutable object representing robot configuration.
 *
 * It is meant to orchestrate FTC Dashboard and other configuration together. Certain sub-objects
 * are annotated with `@Config`. This designates them as FTC Dashboard configuration that can be
 * modified at runtime. **To permanently change these values, you must also modify the code!** The
 * configuration can also change during initialization depending on various build constants like
 * [debug].
 */
object RobotConfig {
    /**
     * When true, enables debugging features like camera streaming and more logs.
     *
     * This should be disabled for competitions.
     */
    const val DEBUG: Boolean = true

    /**
     * Creates a string representing the current robot build constants.
     */
    override fun toString() = "RobotConfig(debug=$DEBUG)"

    /** Example on how to set up a Config Variable.
     @Config
     object NameOfObject {
     /**
     * This is what the variable does.
     */
     @JvmField
     var NAME_OF_VARIABLE: Double = 145.1
     }
     */

    @Config
    object KiwiDrive {
        @JvmField
        var ticksPerRev = 145.1

        @JvmField
        var wheelDiameter = 4.0

        @JvmField
        var maxVelocity = 1150.0

        /** Coefficient of static for the wheel feedforward. */
        @JvmField
        var kS = 0.0

        /** Coefficient of velocity for the wheel feedforward. */
        @JvmField
        var kV = 0.0

        /** Coefficient of acceleration for the wheel feedforward. */
        @JvmField
        var kA = 0.0

        /** Minimum translational velocity, in in/s. */
        @JvmField
        var minTransVel = 20.0

        /** Minimum profile acceleration, in in/s^2. */
        @JvmField
        var minProfileAccel = -30.0

        /** Maximum profile acceleration, in in/s^2. */
        @JvmField
        var maxProfileAccel = 50.0

        /** Maximum angular velocity, in rad/s. */
        @JvmField
        var maxAngVel = PI

        /** Maximum angular acceleration, in rad/s^2. */
        @JvmField
        var maxAngAccel = PI
    }
}
