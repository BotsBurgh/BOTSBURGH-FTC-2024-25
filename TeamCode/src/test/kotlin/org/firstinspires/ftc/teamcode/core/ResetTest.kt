package org.firstinspires.ftc.teamcode.core

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ResetTest {
    // This test creates a resettable and
    // the test should pass as nothing is changed
    @Test
    fun DefaultTest() {
        // The starting value of someState is 5.
        var someState: Int by Resettable { 5 }

        val expected = 5
        val actual = someState
        assertEquals(expected, actual)
    }

    // This test creates a resettable and
    // modifies it and the test should pass
    @Test
    fun modifyingTest() {
        // The starting value of someState is 10.
        var someState: Int by Resettable { 10 }

        fun mutateState() {
            // `Resettable` variables can be mutated as normal.
            someState = 15
            assert(someState == 15)
        }

        // modifying
        mutateState()
        val expected = 15
        val actual = someState
        assertEquals(expected, actual)
    }

    // This test creates a resettable then modifies
    // then resets to original state the test should pass
    @Test
    fun ResetAllTest() {
        // The starting value of someState is 20.
        var someState: Int by Resettable { 20 }

        fun mutateState() {
            // `Resettable` variables can be mutated as normal.
            someState = 25
            assert(someState == 25)
        }

        // modifying
        mutateState()
        // resetting all values
        ResetListener.resetAll()
        val expected = 20
        val actual = someState
        assertEquals(expected, actual)
    }
}
