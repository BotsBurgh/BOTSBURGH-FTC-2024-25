package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.core.API

object ClipMagazine : API() {
    lateinit var latch: Servo
        private set
    lateinit var ejector: Servo
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.latch = this.opMode.hardwareMap.get(Servo::class.java, "clipLatch")
        this.ejector = this.opMode.hardwareMap.get(Servo::class.java, "clipEjector")
    }
}
