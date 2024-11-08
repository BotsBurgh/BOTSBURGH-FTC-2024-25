package org.firstinspires.ftc.teamcode.api
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.core.API

object TankWheel : API() {
    lateinit var left: DcMotorEx
        private set
    lateinit var right: DcMotorEx
        private set

    private val y
        get() = -opMode.gamepad1.left_stick_y.toDouble()
    private val r
        get() = opMode.gamepad1.right_stick_x.toDouble()

    override fun init(opMode: OpMode) {
        super.init(opMode)

        left = this.opMode.hardwareMap.get(DcMotorEx::class.java, "LEFT")
        right = this.opMode.hardwareMap.get(DcMotorEx::class.java, "RIGHT")
        stopAndResetMotors()
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(
        leftPower: Double,
        rightPower: Double,
    ) {
        left.power = leftPower
        right.power = rightPower
    }

    fun drive() {
        power((-y - r), (y - r))
    }

    fun stop() {
        power(0.0, 0.0)
    }

    fun stopAndResetMotors() {
        stop()

        for (motor in arrayOf(left, right)) {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.direction = DcMotorSimple.Direction.FORWARD
        }
    }
}
