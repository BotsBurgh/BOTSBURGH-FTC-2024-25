package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.clamp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.RobotConfig
import kotlin.math.abs

class MotorControllerGroup(
    target: Int,
    pid: PIDCoefficients,
    vararg motors: DcMotor,
) {
    private val controllers = motors.map { MotorController(target, it, pid) }

    fun update() {
        for (controller in controllers) {
            controller.update()
        }
    }

    fun isDone() = controllers.all { it.isDone() }
}

/**
 * A PID controller that moves a motor to a target position.
 *
 * @param target The desired motor encoder position.
 * @param motor The motor that will be controlled.
 * @param pid The PID coefficients.
 * @param threshold The distance the motor has to be within from the target position to be
 *                  considered "done".
 */
class MotorController(
    private val target: Int,
    private val motor: DcMotor,
    private val pid: PIDCoefficients,
    private val threshold: Int = RobotConfig.MotorController.DEFAULT_THRESHOLD,
) {
    private val runtime = ElapsedTime()

    private var previousTime = 0.0
    private var previousError = Int.MAX_VALUE

    private var i = 0.0

    fun update() {
        val currentTime = this.runtime.milliseconds()
        val currentError = this.target - this.motor.currentPosition

        val p = this.pid.p * currentError

        i += this.pid.i * currentError * (currentTime - this.previousTime)

        // Clamp between [-MAX_I, MAX_I]
        this.i = clamp(this.i, -RobotConfig.MotorController.I_LIMIT, RobotConfig.MotorController.I_LIMIT)

        val d = this.pid.d * (currentError - this.previousError) / (currentTime - this.previousTime)

        this.motor.power = clamp(p + this.i + d, -RobotConfig.MotorController.POWER_LIMIT, RobotConfig.MotorController.POWER_LIMIT)

        this.previousTime = currentTime
        this.previousError = currentError
    }

    fun isDone() = abs(this.previousError) < this.threshold
}
