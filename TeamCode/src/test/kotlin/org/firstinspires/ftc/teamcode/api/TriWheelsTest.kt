package org.firstinspires.ftc.teamcode.api

import com.acmerobotics.roadrunner.Vector2d
import org.firstinspires.ftc.teamcode.utils.Polar2d
import org.firstinspires.ftc.teamcode.utils.map
import org.firstinspires.ftc.teamcode.utils.truncate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TriWheelsTest {
    @Test
    fun testInverseZero() {
        val expected = Vector2d(0.0, 0.0)

        // Convert to wheel ratios and back, converting -0.0 to +0.0.
        val ratio = TriWheels.compute(Polar2d.fromCartesian(expected))
        val actual = TriWheels.inverse(ratio)

        assertEquals(expected, actual)
    }

    @Test
    fun testInverse() {
        val expected = Vector2d(-1.0, 1.5)

        // Convert to wheel ratios and back.
        val ratio = TriWheels.compute(Polar2d.fromCartesian(expected))
        val actual = TriWheels.inverse(ratio)

        assertEquals(expected, actual.map { it.truncate(4) })
    }
}
