package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ColorSensor
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.RobotConfig

/* An API to Control the Claw and Color Sensor*/

object Claw :API(){
    lateinit var LeftWheel : Servo
    lateinit var RightWheel : Servo

    lateinit var largeServo : Servo
    lateinit var smallServo : Servo

    lateinit var CSensor : ColorSensor

    override fun init(opMode: OpMode){
        super.init(opMode)

        this.LeftWheel = this.opMode.hardwareMap.get(Servo::class.java, "LCW")
        this.RightWheel = this.opMode.hardwareMap.get(Servo::class.java, "RCW")

        this.largeServo = this.opMode.hardwareMap.get(Servo::class.java, "VServo")
        this.smallServo = this.opMode.hardwareMap.get(Servo::class.java, "HServo")

        this.CSensor = this.opMode.hardwareMap.get(ColorSensor::class.java, "CSensor")

        CSensor.enableLed(false)
        this.reset()

    }
    fun reset(){
        LeftWheel.position
        RightWheel.position

        largeServo.position
        smallServo.position


        CSensor.enableLed(false)
    }

    fun light(){
        CSensor.enableLed(true)
    }
    fun grab(){
        LeftWheel.position = 1.0
        RightWheel.position = 0.0
    }
    fun release(){
        LeftWheel.position = 0.0
        RightWheel.position = 1.0
    }
    fun largeMoveUp(){
        largeServo.position += RobotConfig.Claw.INCRIMENT
    }

    fun largeMoveDown(){
        largeServo.position -= RobotConfig.Claw.INCRIMENT
    }

    fun smallMoveUp(){
        smallServo.position += RobotConfig.Claw.INCRIMENT
    }

    fun smallMoveDown(){
        smallServo.position -= RobotConfig.Claw.INCRIMENT
    }

    fun largeMoveToPos(pos : Double){
        largeServo.position = pos
    }

    fun smallMoveToPos(pos : Double){
        smallServo.position = pos
    }


    fun scan(color:String){
        if(color == "Red" && CSensor.red()> CSensor.blue()){
            grab()
        } else if (color == "Blue" && CSensor.red()< CSensor.blue()){
            grab()
        }
    }


}
