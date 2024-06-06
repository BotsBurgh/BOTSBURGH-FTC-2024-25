@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode.utils

import org.firstinspires.ftc.teamcode.utils.RobotConfig.debug

/**
 * This is an immutable object representing robot configuration.
 *
 * It is meant to orchestrate FTC Dashboard and other configuration together.
 * Certain sub-objects are annotated with `@Config`. This designates them as FTC Dashboard
 * configuration that can be modified at runtime. **To permanently change these values, you must
 * also modify the code!** The configuration can also change during initialization depending on
 * various build constants like [debug].
 */
object RobotConfig {

    /**
     * When true, enables debugging features like camera streaming and more logs.
     *
     * This should be disabled for competitions.
     */
     const val debug: Boolean = true

    /**
     * Creates a string representing the current robot build constants.
     */
     override fun toString() = "RobotConfig(debug=$debug)"
    /** Example on how to set up a Config Variable.
    @Config
    object NAMEOFOBJECT {
        /**
         * This is what the variable does.
        */
        @JvmField
        var NAMEOFVARIABLE: Double = 145.1
    }
     */
}