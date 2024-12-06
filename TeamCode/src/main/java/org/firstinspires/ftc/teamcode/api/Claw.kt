package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.core.API

/**
 * An API for controlling all aspects of the claw.
 */
object Claw: API() {
    lateinit var clawSlide: Servo
        private set
    lateinit var claw: Servo
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        clawSlide = this.opMode.hardwareMap.get(Servo::class.java, "clawSlide")
        claw = this.opMode.hardwareMap.get(Servo::class.java, "claw")
    }

    /**
     * Moves the slide forwards or back. Positive is forward and negative is back.
     */
    fun moveSlide(power: Double) {
        clawSlide.position += power
    }

    /**
     * Opens and closes the claw. Positive is open and negative is close.
     */
    fun moveClaw(power: Double) {
        claw.position += power
    }
}