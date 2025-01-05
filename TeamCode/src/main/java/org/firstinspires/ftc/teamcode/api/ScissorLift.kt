package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.API


object ScissorLift : API() {
    lateinit var motor: DcMotor

    override fun init(opMode: OpMode){
        super.init(opMode)
        this.motor = this.opMode.hardwareMap.get(DcMotor::class.java, "Lift")
    }

    fun lift(){
        this.motor.power = 1.0
    }
    fun unlift(){
        this.motor.power = -1.0
    }
    fun stop(){
        this.motor.power = 0.0
    }
}
