package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Time
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.Vector2dDual
import kotlin.math.PI
import kotlin.math.atan
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class MathTest {
    @Test
    fun testDoubleRoundDecimal() {
        val a = 0.9999999999
        assertEquals(1.0, a.roundDecimal(3))

        val b = 2.1111111111
        assertEquals(2.111, b.roundDecimal(3))

        val c = 1.0
        assertEquals(1.0, c.roundDecimal(3))
    }

    @Test
    fun testVector2dMap() {
        val expected = Vector2d(4.0, 3.0)
        val actual = Vector2d(2.0, 1.5).map { it * 2.0 }

        assertEquals(expected, actual)
    }

    @Test
    fun testDualAtanConsistency() {
        val input = 7.5

        val constantDual = DualNum.constant<Time>(input, 4)
        val variableDual = DualNum.variable<Time>(input, 4)

        // Test that at least the value implementation is correct, even if we can't easily test the
        // derivatives.
        assertEquals(atan(input), constantDual.atan().value())
        assertEquals(atan(input), variableDual.atan().value())
    }

    // Derived from https://github.com/JetBrains/kotlin/blob/58392ba3f3f3e50891c91539d5c586ff4600743f/kotlin-native/runtime/test/numbers/MathTest.kt#L51.
    @Test
    fun testAtan2SpecialCases() {
        // Creates a dual vector, calculates inverse tangent, then returns the value.
        fun atan2Dual(
            y: Double,
            x: Double,
        ) = Vector2dDual.constant<Time>(Vector2d(x, y), 4).atan2().value()

        assertEquals(0.0, atan2Dual(0.0, 0.0))

        assertEquals(0.0, atan2Dual(0.0, 1.0))
        assertEquals(PI, atan2Dual(0.0, -1.0))
        assertEquals(-0.0, atan2Dual(-0.0, 1.0))
        assertEquals(-PI, atan2Dual(-0.0, -1.0))

        // In the original test suite, edge cases for infinity were tested here. They have been
        // skipped, since our implementation for `atan2()` does not support infinity.

        assertEquals(PI / 2, atan2Dual(1.0, 0.0))
        assertEquals(-PI / 2, atan2Dual(-1.0, 0.0))
        assertEquals(PI / 2, atan2Dual(Double.POSITIVE_INFINITY, 1.0))
        assertEquals(-PI / 2, atan2Dual(Double.NEGATIVE_INFINITY, 1.0))

        assertTrue(atan2Dual(Double.NaN, 1.0).isNaN())
        assertTrue(atan2Dual(1.0, Double.NaN).isNaN())
    }
}
