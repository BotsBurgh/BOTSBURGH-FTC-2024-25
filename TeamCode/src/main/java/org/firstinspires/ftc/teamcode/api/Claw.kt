package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ColorSensor
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.RobotConfig

/** An API to Control the Claw and Color Sensor**/

object Claw :API(){
    /**Left Grabber Wheel**/
    lateinit var LeftWheel : Servo
    /**Right Grabber Wheel**/
    lateinit var RightWheel : Servo

    /**Servo on the scissorlift that moves whole arm**/
    lateinit var largeServo : Servo
    /**Servo on the arm that moves claw**/
    lateinit var smallServo : Servo

    /**Color Sensor**/
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

    /**Closes and resets the claw**/
    fun reset(){
        close()
        LeftWheel.position
        RightWheel.position

        largeServo.position
        smallServo.position


        CSensor.enableLed(false)
    }
    /**Turns on the light**/
    fun light(){
        CSensor.enableLed(true)
    }

    /**grabs the sample**/
    fun grab(){
        LeftWheel.position = 1.0
        RightWheel.position = 0.0
    }

    /**Releases the sample**/
    fun release(){
        LeftWheel.position = 0.0
        RightWheel.position = 1.0
    }

    /**Moves the Servo on the scissorlift to a certain position**/
    fun largeMoveToPos(pos : Double){
        largeServo.position = pos
    }

    /**Moves the servo in the middle of the arm to a certain position**/
    fun smallMoveToPos(pos : Double){
        smallServo.position = pos
    }

    /**Grabs the sample if its the right color**/
    fun scan(color:String){
        if(color == "Red" && CSensor.red()> CSensor.blue()){
            grab()
        } else if (color == "Blue" && CSensor.red()< CSensor.blue()){
            grab()
        }
    }

    /**Closes the hook**/
    fun close(){
        smallMoveToPos(RobotConfig.Claw.SMALL_CLOSE_POS)
        largeMoveToPos(RobotConfig.Claw.LARGE_CLOSE_POS)
    }

    /**Opens the hook**/
    fun open(){
        smallMoveToPos(RobotConfig.Claw.SMALL_OPEN_POS)
        largeMoveToPos(RobotConfig.Claw.LARGE_OPEN_POS)
    }




}
