package org.firstinspires.ftc.teamcode.core

import org.firstinspires.ftc.teamcode.withReset
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class DependenciesTest {
    // API to test dependencies
    private class TestAPI(
        val name: String,
        override val dependencies: Set<API> = emptySet(),
    ) : API()

    // This test should pass as everything is initialized
    @Test
    fun dependenciesFulfilled() =
        withReset {
            val apiA = TestAPI("A")
            val apiB = TestAPI("B", setOf(apiA))
            val apiC = TestAPI("C", setOf(apiB))

            Dependencies.registerAPI(apiB)
            Dependencies.registerAPI(apiC)
            Dependencies.registerAPI(apiA)

            // If this fails, it will throw an exception that will cause the test to fail.
            Dependencies.checkDependencies()
        }

    // This test throws a MissingDependency
    @Test
    fun missingDependency() =
        withReset {
            val apiA = TestAPI("A")
            val apiB = TestAPI("B", setOf(apiA))

            // Note how apiA is not registered.
            Dependencies.registerAPI(apiB)

            assertFailsWith<MissingDependency> {
                Dependencies.checkDependencies()
            }
        }
}
