package org.firstinspires.ftc.teamcode.api.funky

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.core.API

object ClipMagazine : API() {
    lateinit var latch: Servo
        private set
    lateinit var ejector: Servo
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.latch = this.opMode.hardwareMap.get(Servo::class.java, "clipLatch")
        this.latch.scaleRange(RobotConfig.ClipMagazine.LATCH_MIN, RobotConfig.ClipMagazine.LATCH_MAX)

        this.ejector = this.opMode.hardwareMap.get(Servo::class.java, "clipEjector")
        this.ejector.scaleRange(RobotConfig.ClipMagazine.EJECTOR_MIN, RobotConfig.ClipMagazine.EJECTOR_MAX)
    }

    fun openLatch() {
        this.latch.position = 0.0
    }

    fun closeLatch() {
        this.latch.position = 1.0
    }

    fun openEjector() {
        this.ejector.position = 0.0
    }

    fun closeEjector() {
        this.ejector.position = 1.0
    }
}
