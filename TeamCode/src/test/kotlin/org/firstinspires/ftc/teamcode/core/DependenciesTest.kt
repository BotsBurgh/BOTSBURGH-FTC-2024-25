package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith


internal class DependenciesTest {

    // API to test Dependencies
    class TestAPI(
        val name: String,
        override val dependencies: Set<API> = emptySet()
    ) : API() {
        override fun init(opMode: OpMode) {
            super.init(opMode)
        }
    }

    // This test should pass as everything is initialized
    @Test
    fun DependencesTest1() {
        val apiA = TestAPI("A")
        val apiB = TestAPI("B", setOf(apiA))
        val apiC = TestAPI("C", setOf(apiB))
        Dependencies.registerAPI(apiB)
        Dependencies.registerAPI(apiC)
        Dependencies.registerAPI(apiA)

        val expected = "No exception thrown"
        val actual = try {
            Dependencies.checkDependencies()
            "No exception thrown"
        } catch (e: Exception) {
            "Exception thrown: ${e.message}"
        }

        assertEquals(expected, actual)

    }

    // This test throws a MissingDependency
    @Test
    fun DependencesTest2() {
        val apiA = TestAPI("A")
        val apiB = TestAPI("B", setOf(apiA))
        Dependencies.registerAPI(apiB)

        assertFailsWith<MissingDependency> {
            Dependencies.checkDependencies()

        }
    }
}
