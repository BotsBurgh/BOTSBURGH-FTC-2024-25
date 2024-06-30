package org.firstinspires.ftc.teamcode.api.roadrunner

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
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.core.API
import org.firstinspires.ftc.teamcode.core.Resettable
import org.firstinspires.ftc.teamcode.utils.RobotConfig

object KiwiLocalizer : API() {
    override val dependencies = setOf(TriWheels)

    private lateinit var red: Encoder
    private lateinit var green: Encoder
    private lateinit var blue: Encoder

    private lateinit var imu: IMU

    private var updated: Boolean by Resettable { false }

    private var lastRedPos = 0
    private var lastGreenPos = 0
    private var lastBluePos = 0

    private lateinit var lastHeading: Rotation2d

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.red = OverflowEncoder(RawEncoder(TriWheels.red))
        this.green = OverflowEncoder(RawEncoder(TriWheels.green))
        this.blue = OverflowEncoder(RawEncoder(TriWheels.blue))

        this.imu = this.opMode.hardwareMap.get(IMU::class.java, "imu")

        this.imu.initialize(
            IMU.Parameters(
                // TODO: Verify orientation of control hub.
                RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                    RevHubOrientationOnRobot.UsbFacingDirection.UP,
                ),
            ),
        )
    }

    fun update(): Twist2dDual<Time> {
        val redPosVel = this.red.getPositionAndVelocity()
        val greenPosVel = this.green.getPositionAndVelocity()
        val bluePosVel = this.blue.getPositionAndVelocity()

        val angles = this.imu.robotYawPitchRollAngles
        val heading = Rotation2d.exp(angles.getYaw(AngleUnit.RADIANS))

        // If this is the first time `update` is called.
        if (!this.updated) {
            this.updated = true

            this.lastRedPos = redPosVel.position
            this.lastGreenPos = greenPosVel.position
            this.lastBluePos = bluePosVel.position

            this.lastHeading = heading

            return Twist2dDual(
                Vector2dDual.constant(Vector2d(0.0, 0.0), 2),
                DualNum.constant(0.0, 2),
            )
        }

        val headingDelta = heading - lastHeading

        // TODO: Calculate radius
        val kinematics = KiwiKinematics(1.0)

        val twist =
            kinematics.forward(
                KiwiKinematics.WheelIncrements(
                    DualNum<Time>(
                        listOf(
                            (redPosVel.position - lastRedPos).toDouble(),
                            redPosVel.velocity.toDouble(),
                        ),
                    ).times(RobotConfig.KiwiDrive.inPerTick),
                    DualNum<Time>(
                        listOf(
                            (greenPosVel.position - lastGreenPos).toDouble(),
                            greenPosVel.velocity.toDouble(),
                        ),
                    ).times(RobotConfig.KiwiDrive.inPerTick),
                    DualNum<Time>(
                        listOf(
                            (bluePosVel.position - lastBluePos).toDouble(),
                            bluePosVel.velocity.toDouble(),
                        ),
                    ).times(RobotConfig.KiwiDrive.inPerTick),
                ),
            )

        lastRedPos = redPosVel.position
        lastGreenPos = greenPosVel.position
        lastBluePos = bluePosVel.position

        lastHeading = heading

        return Twist2dDual(
            twist.line,
            DualNum.cons(headingDelta, twist.angle.drop(1)),
        )
    }
}
