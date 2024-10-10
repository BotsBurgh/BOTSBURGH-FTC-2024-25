package org.firstinspires.ftc.teamcode.api
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.api.TriWheels.stop
import org.firstinspires.ftc.teamcode.core.API

object QuadWheels : API() {
    lateinit var bl: DcMotorEx
        private set
    lateinit var br: DcMotorEx
        private set
    lateinit var fr: DcMotorEx
        private set
    lateinit var fl: DcMotorEx
        private set

    private val y
        get() = -opMode.gamepad1.left_stick_y.toDouble()
    private val r
        get() = opMode.gamepad1.left_stick_x.toDouble()

    override fun init(opMode: OpMode) {
        super.init(opMode)

        bl = this.opMode.hardwareMap.get(DcMotorEx::class.java, "backLeft")
        br = this.opMode.hardwareMap.get(DcMotorEx::class.java, "backRight")
        fr = this.opMode.hardwareMap.get(DcMotorEx::class.java, "frontLeft")
        fl = this.opMode.hardwareMap.get(DcMotorEx::class.java, "frontRight")

        stopAndResetMotors()
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(
        blPower: Double,
        brPower: Double,
        frPower: Double,
        flPower: Double,
    ) {
        bl.power = blPower
        br.power = brPower
        fr.power = frPower
        fl.power = flPower
    }

    fun drive() {
        power((-y + r), (y + r), (y + r), (-y + r))
    }

    fun stop() {
        power(0.0, 0.0, 0.0, 0.0)
    }


    fun stopAndResetMotors() {
        stop()

        for (motor in arrayOf(bl, br, fr, fl)) {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.direction = DcMotorSimple.Direction.FORWARD
        }
    }
}
