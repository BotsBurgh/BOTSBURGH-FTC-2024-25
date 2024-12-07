package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDCoefficients
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.utils.MotorController
import org.firstinspires.ftc.teamcode.utils.MotorControllerGroup

/**
 * An API for manipulating wheels using encoders.
 *
 * This helps when writing autonomous that needs to move a specific distance, or turn a specific
 * amount.
 *
 * Requires the [TriWheels] API.
 *
 * @see driveTo
 * @see spinTo
 */
object Encoders : API() {
    override val isLinear = true

    private fun inchesToTick(inches: Double): Int = (RobotConfig.Encoders.TICKS_PER_INCH * inches).toInt()

    private fun degreesToTick(degrees: Double): Int = (RobotConfig.Encoders.TICKS_PER_DEGREE * degrees).toInt()

    /**
     * Drives the robot in a given [direction] a certain amount of [inches].
     *
     * This function will take full control of the robot, and has a few side-effects. All wheel
     * motors will be reset with [TriWheels.stopAndResetMotors]. Furthermore, this function will not
     * return until the robot has finished moving.
     *
     * Due to current restrictions, [inches] cannot be a negative number.
     */

    /**
     * Spins the robot a certain number of [degrees].
     *
     * Like [driveTo], this function will not return until the robot has finished moving. It will
     * also reset all wheel motors' configuration, including rotation and encoders.
     *
     * Unlike [driveTo], [degrees] can be a negative or positive number. Positive moves the robot
     * **counter-clockwise**, negative moves the robot **clockwise**.
     */

    fun driveTo(
        direction: Direction,
        inches: Double,
    ) {
        TriWheels.stopAndResetMotors()

        val (left, right, back) = this.defineWheels(direction)
        val ticks = this.inchesToTick(inches)

        val controllers = MotorControllerGroup(ticks, PIDCoefficients(0.0004, 0.0000000125, 0.0), left, right)
        val backController =
            MotorController(
                0,
                PIDCoefficients(
                    0.0004,
                    0.0,
                    0.0,
                ),
                back,
            )

        try {
            right.direction = DcMotorSimple.Direction.REVERSE

            while (!(controllers.isDone() && backController.isDone()) && linearOpMode.opModeIsActive()) {
                controllers.update()
                backController.update()

                with(linearOpMode.telemetry) {
                    addData("Status", "Encoder Driving")

                    addData("Left Power", left.power)
                    addData("Right Power", right.power)
                    addData("Back Power", back.power)

                    addData("Target", ticks)

                    addData("Left Pos", left.currentPosition)
                    addData("Right Pos", right.currentPosition)
                    addData("Back Pos", back.currentPosition)

                    update()
                }
            }
        } finally {
            // This reset encoders but also changes the right motor direction back to forward
            TriWheels.stopAndResetMotors()
        }
    }

    fun spinTo(degrees: Double) {
        TriWheels.stopAndResetMotors()

        val ticks = -this.degreesToTick(degrees)

        val controllers = MotorControllerGroup(ticks, PIDCoefficients(0.0006, 0.0, 0.0), TriWheels.red, TriWheels.green, TriWheels.blue)

        try {
            while (
                !controllers.isDone() && linearOpMode.opModeIsActive()
            ) {
                controllers.update()

                with(linearOpMode.telemetry) {
                    addData("Status", "Encoder Rotating")

                    addData(
                        "Power",
                        Triple(TriWheels.red.power, TriWheels.green.power, TriWheels.blue.power),
                    )
                    addData(
                        "Current",
                        Triple(
                            TriWheels.red.currentPosition,
                            TriWheels.green.currentPosition,
                            TriWheels.blue.currentPosition,
                        ),
                    )
                    addData("Target", ticks)

                    update()
                }
            }
        } finally {
            TriWheels.stopAndResetMotors()
        }
    }

    fun dependencies() = setOf(TriWheels)

    /**
     * A function used by [driveTo] to figure out which two wheels are in the front, and which is
     * behind.
     *
     * The returned [Triple] is in the format `Triple<Left, Right, Back>`.
     */
    private fun defineWheels(direction: Direction): Triple<DcMotor, DcMotor, DcMotor> =
        when (direction) {
            Direction.Red -> Triple(TriWheels.blue, TriWheels.green, TriWheels.red)
            Direction.Green -> Triple(TriWheels.red, TriWheels.blue, TriWheels.green)
            Direction.Blue -> Triple(TriWheels.green, TriWheels.red, TriWheels.blue)
        }

    /**
     * An enum representing which axis a robot will drive along in [driveTo].
     *
     * The colors correspond to the wheel names.
     */
    enum class Direction {
        Red,
        Green,
        Blue,
    }
}
