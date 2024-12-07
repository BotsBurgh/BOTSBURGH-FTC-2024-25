package org.firstinspires.ftc.teamcode.core

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ResetTest {
    // This test verifies that `Resettable`s start with the value of their default constructor.
    @Test
    fun testInitializer() {
        // The starting value of `someState` is 5.
        val actual: Int by Resettable { 2 + 3 }

        assertEquals(5, actual)
    }

    // This test ensures that modifying `Resettable`s actually updates their value.
    @Test
    fun testMutation() {
        var actual: Int by Resettable { 10 }

        actual = 15

        assertEquals(15, actual)
    }

    // This test verifies that calling `ResetListener.resetAll()` resets `Resettable`s to their
    // default value.
    @Test
    fun testReset() {
        // The starting value of someState is 20.
        var actual: Int by Resettable { 20 }

        actual = 25
        assertEquals(25, actual)

        Resettable.resetAll()
        assertEquals(20, actual)
    }
}
