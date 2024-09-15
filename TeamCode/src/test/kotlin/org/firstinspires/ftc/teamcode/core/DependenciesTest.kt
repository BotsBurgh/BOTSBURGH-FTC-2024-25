package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.withReset
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

internal class DependenciesTest {
    // API to test dependencies
    private class TestAPI(
        override val dependencies: Set<API> = emptySet(),
    ) : API()

    // This test should pass as everything is initialized
    @Test
    fun dependenciesFulfilled() =
        withReset {
            val apiA = TestAPI()
            val apiB = TestAPI(setOf(apiA))
            val apiC = TestAPI(setOf(apiB))

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
            val apiA = TestAPI()
            val apiB = TestAPI(setOf(apiA))

            // Note how apiA is not registered.
            Dependencies.registerAPI(apiB)

            assertFailsWith<MissingDependency> {
                Dependencies.checkDependencies()
            }
        }

    @Test
    fun registerAddsToDependencyList() =
        withReset {
            val apiA = TestAPI()

            Dependencies.registerAPI(apiA)

            assertContains(Dependencies.initializedAPIs, apiA)
        }

    @Test
    fun initAPIAddsToDependencyList() =
        withReset {
            val apiA = TestAPI()

            apiA.init(
                object : OpMode() {
                    override fun init() {}

                    override fun loop() {}
                },
            )

            assertContains(Dependencies.initializedAPIs, apiA)
        }
}
