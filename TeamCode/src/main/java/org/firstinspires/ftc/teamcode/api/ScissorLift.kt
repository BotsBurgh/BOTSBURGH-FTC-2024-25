package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.teamcode.core.API

object ScissorLift : API() {
    private lateinit var motor: DcMotor
    private lateinit var leftLimit: TouchSensor
    private lateinit var rightLimit: TouchSensor

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.motor = this.opMode.hardwareMap.get(DcMotor::class.java, "liftMotor")
        this.leftLimit = this.opMode.hardwareMap.get(TouchSensor::class.java, "liftLeftLimit")
        this.rightLimit = this.opMode.hardwareMap.get(TouchSensor::class.java, "liftRightLimit")
        this.motor.direction = DcMotorSimple.Direction.REVERSE
    }

    /**
     * Moves the lift with a given power.
     *
     * A positive power will move the lift upwards, closer towards the right limit sensor. A
     * negative power will move the lift downwards, closer towards the left limit sensor.
     *
     * Note that you should call this every update, else [canMove] will not be called and the lift
     * may destroy itself.
     */
    fun lift(power: Double) {
        if (canMove(power)) {
            this.motor.power = power
        } else {
            this.motor.power = 0.0
        }
    }

    /**
     * Returns true if the [motor] can be spun in a given direction without hitting one of the limit
     * sensors.
     *
     * Note that this will return false if power is zero.
     */
    fun canMove(power: Double): Boolean =
        if (power > 0 && !this.rightLimit.isPressed) {
            true
        } else if (power < 0 && !this.leftLimit.isPressed) {
            true
        } else {
            false
        }
}