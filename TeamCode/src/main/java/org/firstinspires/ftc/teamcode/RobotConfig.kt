// We are treating config fields as constant even through they may be changed, which is why we use
// SCREAMING_SNAKE_CASE for them instead of `ktlint`'s desired lowerCamelCase.
@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.io.File
import kotlin.math.PI

/**
 * This is an immutable object representing robot configuration.
 *
 * It is meant to orchestrate FTC Dashboard and other configuration together. Certain sub-objects
 * are annotated with `@Config`. This designates them as FTC Dashboard configuration that can be
 * modified at runtime. **To permanently change these values, you must also modify the code!** The
 * configuration can also change during initialization depending on various build constants like
 * [DEBUG].
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

    /** Configuration related to the TeleOpMain opmode. */
    @Config
    object TeleOpMain {
        /** A multiplier that scales that robot's driving / strafing speed. */
        @JvmField
        var DRIVE_SPEED: Double = 0.6

        /** A multiplier that scales the robot's rotation speed. */
        @JvmField
        var ROTATE_SPEED: Double = 0.3
    }

    // TODO: Set robot radius and inches per tick.
    @Config
    object KiwiLocalizer {
        /** The radius of the robot, from the center to the wheel. */
        @JvmField
        var RADIUS: Double = 6.0

        /** The amount of inches a wheel travels in a single tick. */
        @JvmField
        var INCHES_PER_TICK: Double = 0.082191780821918

        @JvmField
        var LOGO_FACING_DIRECTION: RevHubOrientationOnRobot.LogoFacingDirection =
            RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD

        @JvmField
        var USB_FACING_DIRECTION: RevHubOrientationOnRobot.UsbFacingDirection =
            RevHubOrientationOnRobot.UsbFacingDirection.UP
    }

    @Config
    object KiwiDrive {
        // Feedforward
        @JvmField
        var K_S: Double = 0.0

        @JvmField
        var K_V: Double = 0.0

        @JvmField
        var K_A: Double = 0.0

        // Path profile (in inches)
        @JvmField
        var MAX_WHEEL_VEL: Double = 50.0

        @JvmField
        var MIN_PROFILE_ACCEL: Double = -30.0

        @JvmField
        var MAX_PROFILE_ACCEL: Double = 50.0

        // Turn profile (in radians)
        @JvmField
        var MAX_ANG_VEL: Double = PI

        @JvmField
        var MAX_ANG_ACCEL: Double = PI

        // Path controller gains
        @JvmField
        var AXIAL_GAIN: Double = 0.0

        @JvmField
        var HEADING_GAIN: Double = 0.0

        @JvmField
        var AXIAL_VEL_GAIN: Double = 0.0

        @JvmField
        var HEADING_VEL_GAIN: Double = 0.0
    }

    @Config
    object Logging {
        /**
         * The root folder for all Botsburgh-specific files, accessible at `/sdcard/BotsBurgh` on
         * the robot.
         */
        @JvmField
        var BOTSBURGH_FOLDER = File(AppUtil.ROOT_FOLDER, "/BotsBurgh")

        /** The folder where all logs are stored. */
        @JvmField
        var LOG_FOLDER = File(BOTSBURGH_FOLDER, "logs")

        @JvmField
        var TELEMETRY_CAPACITY = 5

        @JvmField
        var TELEMETRY_ORDER = Telemetry.Log.DisplayOrder.OLDEST_FIRST
    }
}
