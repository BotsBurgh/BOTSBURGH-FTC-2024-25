package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.teamcode.core.API

/** API to control the Scissorlift**/
object ScissorLift : API() {
    /** Motor that lifts/collapses Scissorlift**/
    lateinit var motor: DcMotor

    /**Touch sensor that manually stops the scissorlift from destroying itself**/
    private lateinit var minLift: TouchSensor
    /**Touch sensor that manually stops the scissorlift from destroying itself**/
    private lateinit var maxLift: TouchSensor

    override fun init(opMode: OpMode){
        super.init(opMode)
        this.motor = this.opMode.hardwareMap.get(DcMotor::class.java, "Lift")
        this.minLift = this.opMode.hardwareMap.get(TouchSensor::class.java, "minLift")
        this.maxLift = this.opMode.hardwareMap.get(TouchSensor::class.java, "maxLift")

//        stopAndResetMotor()
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }

    /**Lifts if the corresponding Touchsensor is not being touched**/
    fun lift(pwr: Double){
        if(!maxLift.isPressed) {
            this.motor.power = pwr
        }
    }

    /**Lifts if the corresponding Touchsensor is not being touched**/
    fun unlift(pwr: Double){

        if(!minLift.isPressed){
            this.motor.power = pwr
        }
    }

    /** Emergency stop on the scissorlift**/
    fun stop(){
        this.motor.power = 0.0
    }

    fun getLockPos(): Int{
        return this.motor.currentPosition
    }

    fun goToPos(lockVal: Int){
        if (motor.currentPosition < lockVal) {
            while (motor.currentPosition < lockVal){
                lift(1.0)
            }
        }
        else if(motor.currentPosition > lockVal){
            while (motor.currentPosition > lockVal){
                unlift(1.0)
            }
        }
    }
    /**Stops and resets the motor**/
    fun stopAndResetMotor() {
        stop()

        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        motor.direction = DcMotorSimple.Direction.FORWARD

    }

    /**Checks if motor is powered**/
    fun isPowered() : Boolean{
        return motor.power != 0.0
    }
}
