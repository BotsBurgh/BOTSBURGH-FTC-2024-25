package org.firstinspires.ftc.teamcode.api.roadrunner.kiwi

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Rotation2d
import com.acmerobotics.roadrunner.Time
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.Vector2dDual
import com.acmerobotics.roadrunner.ftc.Encoder
import com.acmerobotics.roadrunner.ftc.OverflowEncoder
import com.acmerobotics.roadrunner.ftc.RawEncoder
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.RobotConfig
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.core.API

/**
 * An [API] used to detect where the robot is and how much it has moved.
 *
 * @see update
 */
object KiwiLocalizer : API() {
    override val dependencies = setOf(TriWheels)

    private lateinit var red: Encoder
    private lateinit var green: Encoder
    private lateinit var blue: Encoder

    private lateinit var imu: IMU

    lateinit var kinematics: KiwiKinematics

    private var lastRedPos = 0
    private var lastGreenPos = 0
    private var lastBluePos = 0

    private var lastHeading = Rotation2d.fromDouble(0.0)

    private var firstUpdate = true

    override fun init(opMode: OpMode) {
        super.init(opMode)

        red = OverflowEncoder(RawEncoder(TriWheels.red))
        green = OverflowEncoder(RawEncoder(TriWheels.green))
        blue = OverflowEncoder(RawEncoder(TriWheels.blue))

        imu = this.opMode.hardwareMap.get(IMU::class.java, "imu")
        imu.initialize(
            IMU.Parameters(
                RevHubOrientationOnRobot(
                    RobotConfig.KiwiLocalizer.LOGO_FACING_DIRECTION,
                    RobotConfig.KiwiLocalizer.USB_FACING_DIRECTION,
                ),
            ),
        )
        imu.resetYaw()

        kinematics = KiwiKinematics(RobotConfig.KiwiLocalizer.RADIUS)
    }

    /**
     * Returns a [Twist2dDual] representing how much the robot's position and heading has changed
     * compared to the previous time this function was called.
     */
    fun update(): Twist2dDual<Time> {
        // Find position and velocity of each wheel.
        val redPosVel = red.getPositionAndVelocity()
        val greenPosVel = green.getPositionAndVelocity()
        val bluePosVel = blue.getPositionAndVelocity()

        // Find heading of IMU.
        val angles = imu.robotYawPitchRollAngles
        val heading = Rotation2d.fromDouble(angles.getYaw(AngleUnit.RADIANS))

        if (firstUpdate) {
            firstUpdate = false

            // Initialize last position and heading as current values.
            lastRedPos = redPosVel.position
            lastGreenPos = greenPosVel.position
            lastBluePos = bluePosVel.position

            lastHeading = heading

            // Return a zeroed twist, equivalent no change.
            return Twist2dDual(
                Vector2dDual.constant(Vector2d(0.0, 0.0), 2),
                DualNum.constant(0.0, 2),
            )
        }

        // Find the change in heading.
        val headingDelta = heading - lastHeading

        // Calculate the twist: how much the robot has moved between the current and previous call
        // to `update()`.
        val twist =
            kinematics.forward(
                KiwiKinematics.WheelTicks(
                    DualNum<Time>(
                        doubleArrayOf(
                            (redPosVel.position - lastRedPos).toDouble(),
                            redPosVel.velocity.toDouble(),
                        ),
                    ) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                    DualNum<Time>(
                        doubleArrayOf(
                            (greenPosVel.position - lastGreenPos).toDouble(),
                            greenPosVel.velocity.toDouble(),
                        ),
                    ) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                    DualNum<Time>(
                        doubleArrayOf(
                            (bluePosVel.position - lastBluePos).toDouble(),
                            bluePosVel.velocity.toDouble(),
                        ),
                    ) * RobotConfig.KiwiLocalizer.INCHES_PER_TICK,
                ),
            )

        // Update last position and heading to current values.
        lastRedPos = redPosVel.position
        lastGreenPos = greenPosVel.position
        lastBluePos = bluePosVel.position

        lastHeading = heading

        // Return the calculated twist, but replace the calculated angle with the one from the IMU.
        return Twist2dDual(twist.line, DualNum.cons(headingDelta, twist.angle.drop(1)))
    }
}
