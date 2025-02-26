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

        this.largeServo = this.opMode.hardwareMap.get(Servo::class.java, "largeServo")
        this.smallServo = this.opMode.hardwareMap.get(Servo::class.java, "smallServo")

        this.CSensor = this.opMode.hardwareMap.get(ColorSensor::class.java, "CSensor")

        CSensor.enableLed(false)
//        this.reset()

    }
    fun reset(){
        close()
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

    //Closes the hook
    fun close(){
        smallMoveToPos(RobotConfig.Claw.SMALL_CLOSE_POS)
        largeMoveToPos(RobotConfig.Claw.LARGE_CLOSE_POS)
    }

    //Extends the hook to the bar for sample scoring(Scissorlift down)
    fun open(){
        smallMoveToPos(RobotConfig.Claw.SMALL_OPEN_POS)
        largeMoveToPos(RobotConfig.Claw.LARGE_OPEN_POS)
    }




}
