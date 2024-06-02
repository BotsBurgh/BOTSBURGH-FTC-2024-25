//Code from 2023-2024 Botsburgh GitHub
// https://github.com/BotsBurgh/BOTSBURGH-FTC-2023-24


@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.utils.RobotConfig.debug
import org.opencv.core.Rect
import org.opencv.core.Scalar

/**
 * This is an immutable object representing robot configuration.
 *
 * It is meant to orchestrate FTC Dashboard and other configuration together.
 *
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

    /** Configuration related to moving the wheels using encoders. */
    @Config
    object Encoders {
        /**
         * How many ticks a wheel needs to rotate for the robot to travel an inch when moving along
         * one of it's three axis.
         *
         * This value was calculated by getting the encoder resolution and dividing by the wheels
         * circumference.
         */
        @JvmField
        var TICKS_PER_INCH: Double = 145.1 / (1.5 * 2 * Math.PI)

        /**
         * How many ticks a wheel needs to rotate for the robot to spin a single degree.
         *
         * This value was calculated by guessing and checking, and may be further changed to
         * increase accuracy.
         */
        @JvmField
        var TICKS_PER_DEGREE: Double = 1.82

        /**
         * A multiplier that calculates the power of the wheel relative to the amount it needs to
         * rotate.
         */
        @JvmField
        var ENCODER_GAIN: Double = 0.0003

        /**
         * How many ticks a wheel needs to be within the target to be considered finished.
         */
        @JvmField
        var ENCODER_ERROR: Int = 10

        /**
         * The maximum power a wheel can spin at when the robot is driving with encoders.
         */
        @JvmField
        var MAX_DRIVE_SPEED: Double = 0.3

        /**
         * The maximum power a wheel can spin at when the robot spinning with encoders.
         */
        @JvmField
        var MAX_SPIN_SPEED: Double = 0.4

        /**
         * A multiplier that calculates the power of the wheel relative to the amount of time that
         * has passed.
         */
        @JvmField
        var TIME_GAIN: Double = 0.4
    }

    /** Configuration related to moving using april tags. */
    @Config
    object AprilMovement {
        // Multipliers against error values to proportionally control the speed of the robot.

        @JvmField
        var RANGE_GAIN: Double = 0.003

        @JvmField
        var HEADING_GAIN: Double = 0.003

        @JvmField
        var STRAFE_GAIN: Double = 0.002

        // A threshold that the error must be below in order for the movement to finish.

        @JvmField
        var RANGE_ERROR: Double = 2.0

        @JvmField
        var HEADING_ERROR: Double = 5.0

        @JvmField
        var STRAFE_ERROR: Double = 5.0
    }

    /** Configuration related to scanning april tags. */
    @Config
    object AprilVision {
        /**
         * Sets how many milliseconds of exposure should be used when scanning april tags.
         *
         * In general, a lower exposure is better for bright areas and higher exposure is better for
         * darker areas. A higher exposure also increases motion blur, which is why in the
         * configuration it is set to a low number.
         *
         * This will only be applied if `Vision.optimizeForAprilTags` is called.
         */
        @JvmField
        var OPTIMUM_EXPOSURE: Long = 3

        /**
         * Sets the gain that should be used when scanning april tags.
         *
         * Gain is an offset used to artificially increase or decrease the brightness of an image.
         *
         * This will only be applied if `Vision.optimizeForAprilTags` is called.
         */
        @JvmField
        var OPTIMUM_GAIN: Int = 255
    }

    /** Configuration related to the `AutoMain` opmode. */
    @Config
    object AutoMain {
        /** A time in milliseconds that the robot waits before it starts moving in autonomous. */
        @JvmField
        var WAIT_TIME: Long = 0
    }

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

    /** Configuration related to the Drone API. */
    @Config
    object Drone {
        @JvmField
        var OPEN_PIN: Double = 0.05

        @JvmField
        var CLOSE_PIN: Double = 0.4
    }

    /** Configuration related to the CubeVision API. */
    @Config
    object CubeVision {
        @JvmField
        var LEFT_REGION = Rect(50, 285, 75, 75)

        @JvmField
        var CENTER_REGION = Rect(295, 265, 75, 75)

        @JvmField
        var RIGHT_REGION = Rect(510, 275, 75, 75)

        @JvmField
        var RED_WEIGHT = Scalar(1.0, -0.5, -0.5)

        @JvmField
        var BLUE_WEIGHT = Scalar(-0.5, -0.5, 1.0)
    }

    @Config
    object PixelPlacer {
        @JvmField
        var DEFAULT_POSITION = 0.45

        @JvmField
        var PLACE_POSITION = 0.85
    }

    @Config
    object MotorController {
        @JvmField
        var POWER_LIMIT = 1.0

        @JvmField
        var I_LIMIT = 0.1
    }
}