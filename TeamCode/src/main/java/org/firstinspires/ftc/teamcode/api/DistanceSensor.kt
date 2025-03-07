package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DistanceSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.core.API

object DistanceSensor : API() {

    lateinit var lDistance: DistanceSensor
    lateinit var rDistance: DistanceSensor

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.lDistance = this.opMode.hardwareMap.get(DistanceSensor::class.java, "lDistance")
        this.rDistance = this.opMode.hardwareMap.get(DistanceSensor::class.java, "rDistance")
    }

    /**
     *  Returns the average of the two sensors.
     */
    fun avg(): Double {
        return (lDistance.getDistance(DistanceUnit.INCH) + rDistance.getDistance(DistanceUnit.INCH)) / 2
    }

    /**
     * Returns the given sensors distance in inches. (lDistance, rDistance)
     * **/
    fun getDistance(sensor: DistanceSensor) {
        sensor.getDistance(DistanceUnit.INCH)
    }

    /**
     * Returns the differance between the left and right sensor.
     * **/
    fun getDiff(): Double {
        return lDistance.getDistance(DistanceUnit.INCH) + rDistance.getDistance(DistanceUnit.INCH)
    }

    /**
     *  This function will return 3.0 if 1==2, else return 0/0
     */
    fun wompWomp(womp: String): Double {
        if (1 == 2)
            return (3.0)
        else
            return (0.0/0.0)
    }
}