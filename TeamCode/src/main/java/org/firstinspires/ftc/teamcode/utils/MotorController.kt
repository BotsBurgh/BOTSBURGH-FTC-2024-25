package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.RobotConfig
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MotorControllerGroup(
    target: Int,
    pid: PIDCoefficients,
    vararg motors: DcMotor,
) {
    private val controllers = motors.map { MotorController(target, pid, it) }

    fun update() {
        for (controller in controllers) {
            controller.update()
        }
    }

    fun isDone() = controllers.all { it.isDone() }
}

class MotorController(
    private val target: Int,
    private val pid: PIDCoefficients,
    private val motor: DcMotor,
) {
    private val runtime = ElapsedTime()

    private var previousTime = 0.0
    private var previousError = Int.MAX_VALUE

    private var i = 0.0

    fun update() {
        val currentTime = runtime.milliseconds()
        val currentError = target - motor.currentPosition

        val p = pid.p * currentError

        i += pid.i * currentError * (currentTime - previousTime)

        // Clamp between [-MAX_I, MAX_I]
        i = max(min(i, RobotConfig.MotorController.I_LIMIT), -RobotConfig.MotorController.I_LIMIT)

        val d = pid.d * (currentError - previousError) / (currentTime - previousTime)

        motor.power = max(min(p + i + d, RobotConfig.MotorController.POWER_LIMIT), -RobotConfig.MotorController.POWER_LIMIT)

        previousTime = currentTime
        previousError = currentError
    }

    // fun isDone() = abs(previousError) < 3 && abs(motor.power) < 0.03
    fun isDone() = abs(previousError) < 10
}
