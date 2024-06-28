package org.firstinspires.ftc.teamcode.api.roadrunner

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.Vector2dDual

data class KiwiKinematics(val radius: Double) {
    data class WheelIncrements<Param>(
        val red: DualNum<Param>,
        val green: DualNum<Param>,
        val blue: DualNum<Param>,
    )

    fun <Param> forward(w: WheelIncrements<Param>) =
        Twist2dDual(
            Vector2dDual(
                TODO("x"),
                TODO("y"),
            ),
            // Sum of all increments define rotation.
            w.red + w.green + w.blue,
        )
}
