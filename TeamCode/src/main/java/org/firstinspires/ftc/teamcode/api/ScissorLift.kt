package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.API

object ScissorLift : API() {
    lateinit var motor: DcMotor
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.motor = this.opMode.hardwareMap.get(DcMotor::class.java, "liftMotor")
        this.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        this.motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
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
        this.motor.power = power
    }
}
