// We are treating config fields as constant even through they may be changed, which is why we use
// SCREAMING_SNAKE_CASE for them instead of `ktlint`'s desired lowerCamelCase.
@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.hardware.sparkfun.SparkFunOTOS
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import org.firstinspires.ftc.teamcode.core.logging.Level
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
        var DRIVE_SPEED: Double = 1.0

        /** A multiplier that scales the robot's rotation speed. */
        @JvmField
        var ROTATE_SPEED: Double = 0.5
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

        @JvmField
        var FILTER_LEVEL = Level.Info
    }

    @Config
    object MotorController {
        @JvmField
        var POWER_LIMIT = 1.0

        @JvmField
        var I_LIMIT = 0.1
    }

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
        var TICKS_PER_INCH: Double = 420.0 / (1.5 * 2 * Math.PI)

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
        var MAX_DRIVE_SPEED: Double = 0.4 //TODO: Change as needed

        /**
         * The maximum power a wheel can spin at when the robot spinning with encoders.
         */
        @JvmField
        var MAX_SPIN_SPEED: Double = 0.5 //TODO: Change as needed

        /**
         * A multiplier that calculates the power of the wheel relative to the amount of time that
         * has passed.
         */
        @JvmField
        var TIME_GAIN: Double = 0.4
    }

    @Config
    object Claw{
        /*How short we can make the claw*/

        @JvmField
        var CLAW_MIN_HEIGHT: Double = 0.7

        /*How tall we can make the claw*/


        @JvmField
        var CLAW_MAX_HEIGHT: Double = 0.4

        /*The incriment for the claw to rotate*/
        @JvmField
        var INCRIMENT: Double = 0.2

        @JvmField
        var CLAW_LEFT_TURN: Double = 0.14

        @JvmField
        var CLAW_RIGHT_TURN: Double = 0.52
    }

    @Config
    object CloseAutonomous{
        //Using variables for distances, so that it can be quickly modded by RobotConfig

        @JvmField
        var FIRST_TURN: Double = 90.0

        @JvmField
        var FORWARD: Double = 24.0

        @JvmField
        var SMALL_TURN: Double = 20.0
    }

    @Config
    object OTOS {
        @JvmField
        var OFFSET = SparkFunOTOS.Pose2D(0.0, 0.0 ,-42.0)

        @JvmField
        var LINEAR_SCALAR: Double = 1.0

        @JvmField
        var ANGULAR_SCALAR: Double = 1.0

        @JvmField
        var SPEED_GAIN: Double = 0.04

        @JvmField
        var MAX_AUTO_SPEED: Double = 0.4

        @JvmField
        var STRAFE_GAIN: Double = 0.04

        @JvmField
        var MAX_AUTO_STRAFE: Double = 0.4

        @JvmField
        var TURN_GAIN: Double = 0.04

        @JvmField
        var MAX_AUTO_TURN: Double = 0.4

        @JvmField
        var MAGNITUDE: Double = 0.5
    }
}
