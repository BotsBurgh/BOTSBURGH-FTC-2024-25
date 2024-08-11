package org.firstinspires.ftc.teamcode.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class IsNullTest {
    private val a: Int? = null
    private val b: Int = 10

    @Test
    fun testIsNull() {
        assertTrue(a.isNull())
        assertFalse(b.isNull())
    }

    @Test
    fun testIsNotNull() {
        assertFalse(a.isNotNull())
        assertTrue(b.isNotNull())
    }
}
