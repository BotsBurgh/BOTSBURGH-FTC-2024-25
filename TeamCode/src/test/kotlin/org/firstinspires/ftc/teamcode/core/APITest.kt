package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

internal class APITest {
    private open class EmptyAPI : API() {
        fun accessOpMode() = this.opMode

        fun accessLinearOpMode() = this.linearOpMode
    }

    private class EmptyLinearAPI : EmptyAPI() {
        override val isLinear = true
    }

    private class EmptyOpMode : OpMode() {
        override fun init() {}

        override fun loop() {}
    }

    private class EmptyLinearOpMode : LinearOpMode() {
        override fun runOpMode() {}
    }

    @Test
    fun testAPIOpModeAccess() {
        val api = EmptyAPI()
        val opMode = EmptyOpMode()

        api.init(opMode)

        assertTrue(api.isInit)
        assertSame(opMode, api.accessOpMode())
    }

    @Test
    fun testLinearAPIOpModeAccess() {
        val linearAPI = EmptyLinearAPI()
        val linearOpMode = EmptyLinearOpMode()

        linearAPI.init(linearOpMode)

        assertSame(linearOpMode, linearAPI.accessOpMode())
        assertSame(linearOpMode, linearAPI.accessLinearOpMode())
    }

    @Test
    fun testAPINotInitialized() {
        val api = EmptyAPI()

        assertFalse(api.isInit)
        assertFailsWith<APINotInitialized> { api.accessOpMode() }
    }

    @Test
    fun testInitAPIMoreThanOnce() {
        val api = EmptyAPI()
        val opMode = EmptyOpMode()

        // Initialize API once.
        api.init(opMode)

        assertFailsWith<InitAPIMoreThanOnce> {
            // Initialize API twice.
            api.init(opMode)
        }
    }

    @Test
    fun testIllegalLinearOpModeAccess() {
        // A non-linear API.
        val api = EmptyAPI()

        assertFailsWith<IllegalLinearOpModeAccess> { api.accessLinearOpMode() }
    }

    @Test
    fun testInitLinearAPIWithoutLinearOpMode() {
        val linearAPI = EmptyLinearAPI()
        // A non-linear opmode.
        val opMode = EmptyOpMode()

        assertFailsWith<InitLinearAPIWithoutLinearOpMode> { linearAPI.init(opMode) }
    }
}
