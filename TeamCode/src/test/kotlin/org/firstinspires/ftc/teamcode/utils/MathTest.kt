package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.Vector2d
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MathTest {
    @Test
    fun testDoubleTruncate() {
        val a = 0.9999999999
        assertEquals(1.0, a.truncate(3))

        val b = 2.1111111111
        assertEquals(2.111, b.truncate(3))

        val c = 1.0
        assertEquals(1.0, c.truncate(3))
    }

    @Test
    fun testVector2dMap() {
        val expected = Vector2d(4.0, 3.0)
        val actual = Vector2d(2.0, 1.5).map { it * 2.0 }

        assertEquals(expected, actual)
    }
}
