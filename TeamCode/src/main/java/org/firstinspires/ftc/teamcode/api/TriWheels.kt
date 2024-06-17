package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.core.API
import kotlin.math.PI
import kotlin.math.sin

/**
 * An API for controlling the wheels of the triangle robot.
 */
object TriWheels : API() {
    lateinit var red: DcMotorEx
        private set
    lateinit var green: DcMotorEx
        private set
    lateinit var blue: DcMotorEx
        private set

    // The angles of each wheel in radians, where red is forward.
    private const val RED_ANGLE: Double = PI / 2.0
    private const val GREEN_ANGLE: Double = PI * (11.0 / 6.0)
    private const val BLUE_ANGLE: Double = PI * (7.0 / 6.0)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        red = this.opMode.hardwareMap.get(DcMotorEx::class.java, "redWheel")
        green = this.opMode.hardwareMap.get(DcMotorEx::class.java, "greenWheel")
        blue = this.opMode.hardwareMap.get(DcMotorEx::class.java, "blueWheel")

        stopAndResetMotors()
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(
        redPower: Double,
        greenPower: Double,
        bluePower: Double,
    ) {
        red.power = redPower
        green.power = greenPower
        blue.power = bluePower
    }

    /**
     * Rotates all of the wheels with a given [power].
     */
    fun rotate(power: Double) {
        power(power, power, power)
    }

    /**
     * Makes the robot drive in a certain direction [radians] with a given strength [magnitude].
     *
     * If [rotation] is specified, it acts as a offset applied to each wheel to make the entire
     * robot spin.
     */
    fun drive(
        radians: Double,
        magnitude: Double,
        rotation: Double = 0.0,
    ) {
        val (r, g, b) = wheelRatio(radians, magnitude, rotation)
        power(r, g, b)
    }

    /**
     * Makes all 3 wheels stop.
     *
     * This is shorthand for setting the power to 0.
     */
    fun stop() {
        power(0.0, 0.0, 0.0)
    }

    /**
     * This function resets all 3 motors.
     *
     * That includes:
     *
     * - Their speed.
     * - Their encoder position.
     * - Their run mode.
     * - Their braking behavior.
     * - Their direction.
     *
     * If you manually changed something like a motor's direction, you'll have to do it again after
     * calling this function.
     */
    fun stopAndResetMotors() {
        stop()

        for (motor in arrayOf(red, green, blue)) {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.direction = DcMotorSimple.Direction.FORWARD
        }
    }

    /**
     * Returns the ratio of how much each wheel should spin for given angle in [radians].
     *
     * The [magnitude] can be used to scale the ration (make it faster / larger), and the [rotation]
     * can be used to add a constant offset (to make it spin while traveling in a direction).
     *
     * For a visual demonstration of what this is doing, see
     * [this Desmos graph](https://www.desmos.com/geometry/b6h0f7fjls).
     */
    private fun wheelRatio(
        radians: Double,
        magnitude: Double,
        rotation: Double = 0.0,
    ): Triple<Double, Double, Double> =
        Triple(
            magnitude * sin(RED_ANGLE - radians) + rotation,
            magnitude * sin(GREEN_ANGLE - radians) + rotation,
            magnitude * sin(BLUE_ANGLE - radians) + rotation,
        )
}
