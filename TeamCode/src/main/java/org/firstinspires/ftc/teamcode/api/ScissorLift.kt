package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.teamcode.core.API


object ScissorLift : API() {
    lateinit var motor: DcMotor

    private lateinit var minLift: TouchSensor
    private lateinit var maxLift: TouchSensor

    override fun init(opMode: OpMode){
        super.init(opMode)
        this.motor = this.opMode.hardwareMap.get(DcMotor::class.java, "Lift")
        this.minLift = this.opMode.hardwareMap.get(TouchSensor::class.java, "minLift")
        this.maxLift = this.opMode.hardwareMap.get(TouchSensor::class.java, "maxLift")
    }

    fun lift(pwr: Double){

        if(!maxLift.isPressed && !minLift.isPressed) {
            this.motor.power = pwr
        }else{
            this.motor.power = 0.0
        }
    }

    fun stop(){
        this.motor.power = 0.0
    }
}
