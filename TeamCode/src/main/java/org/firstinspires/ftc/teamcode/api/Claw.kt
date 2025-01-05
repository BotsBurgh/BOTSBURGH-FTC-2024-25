package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ColorSensor
import org.firstinspires.ftc.teamcode.core.API

/* An API to Control the Claw and Color Sensor*/

object Claw :API(){
    lateinit var LeftWheel : Servo
    lateinit var RightWheel : Servo

    lateinit var VerticalServo : Servo
    lateinit var HorizontalServo : Servo

    lateinit var rackNPinion : Servo

    lateinit var CSensor : ColorSensor

    override fun init(opMode: OpMode){
        super.init(opMode)

        this.LeftWheel = this.opMode.hardwareMap.get(Servo::class.java, "LCW")
        this.RightWheel = this.opMode.hardwareMap.get(Servo::class.java, "RCW")

        this.VerticalServo = this.opMode.hardwareMap.get(Servo::class.java, "VServo")
        this.HorizontalServo = this.opMode.hardwareMap.get(Servo::class.java, "HServo")

        this.rackNPinion = this.opMode.hardwareMap.get(Servo::class.java, "R&P")

        this.CSensor = this.opMode.hardwareMap.get(ColorSensor::class.java, "CSensor")

        CSensor.enableLed(false)
        this.reset()

    }
    fun reset(){
        LeftWheel.position=0.0
        RightWheel.position=0.0

        VerticalServo.position=0.0
        HorizontalServo.position=0.0

        rackNPinion.position=0.0

        CSensor.enableLed(false)
    }

    fun light(){
        CSensor.enableLed(true)
    }
    fun grab(){
        LeftWheel.position = 1.0
        RightWheel.position = 1.0
    }
    fun release(){
        LeftWheel.position = 0.0
        RightWheel.position = 0.0
    }
    fun verticalMove(pwr : Double){
        VerticalServo.position = pwr
    }
    fun horizontalMove(pwr : Double){
        HorizontalServo.position = pwr
    }
    fun extend(){
        rackNPinion.position = 1.0
    }

    fun retract(){
        rackNPinion.position = 0.0
    }

    fun scan(color:String){
        if(color == "Red"){
            if(CSensor.red()> CSensor.blue()){
                grab()
            }
        } else if (color == "Blue"){
            if(CSensor.red()< CSensor.blue()){
                grab()
            }
        }
    }


}