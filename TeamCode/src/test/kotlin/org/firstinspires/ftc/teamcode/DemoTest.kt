package org.firstinspires.ftc.teamcode

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

// This is an example test! All tests functions must be inside a class, which in this case we mark
// as `internal`. The `@Ignore` annotation is there so these tests are not run by default, since one
// of them fails on purpose. When testing these changes locally, you may remove this annotation and
// run the `testDebugUnitTest` task through Gradle.
@Ignore
internal class DemoTest {
    // Tests are functions that take no arguments and return nothing. They must be marked with
    // `@Test` to be run.
    @Test
    fun succeeds() {
        // Tests are structured in three parts. First, we setup the test. This involves any sort of
        // initialization, but is also where we define the expected value.
        val expected = "BotsBurgh"

        // Next, we actual do the testing. In this case, we're testing a function that joins two
        // strings together. We store the result in a variable.
        val actual = concatenate("Bots", "Burgh")

        // Finally, we assert that the expected result is equal to the actual result. This function
        // will raise a warning if this is false, helping us catch any issues. You must call one of
        // the assert functions within a test, or the results will be ignored.
        assertEquals(expected, actual)
    }

    // This test is similar to before, but the assertion actually fails. Make sure to run this
    // locally, so you can see what erroneous output looks like!
    @Test
    fun fails() {
        // We prepare to test, as well as define the expected value.
        val expected = "Hello, world!"

        // We run the actual test. In this case, we forgot the space in the middle of "Hello,
        // world!"
        val actual = concatenate("Hello,", "world!")

        // Finally, we assert that they are both equal. This will evaluate as false, causing the
        // test to fail. As an experiment, try commenting out this line! Does the test still fail?
        assertEquals(expected, actual)
    }

    // This is the function that we are testing. It combines two strings together using the `+`
    // operator.
    private fun concatenate(
        a: String,
        b: String,
    ): String {
        // If this were to change, the above tests may fail! Try commenting out this line and
        // uncommenting one after, and see what happens to the tests. (Make sure to remove `@Ignore`
        // too!)
        return a + b
        // return b + a
    }
}
