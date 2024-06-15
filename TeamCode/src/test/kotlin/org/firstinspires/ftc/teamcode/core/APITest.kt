package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlin.test.Test
import kotlin.test.assertFailsWith

class APITest {
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

    @Test
    fun testAPINotInitialized() {
        val api = EmptyAPI()

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
        val api = EmptyAPI()

        assertFailsWith<IllegalLinearOpModeAccess> { api.accessLinearOpMode() }
    }

    @Test
    fun testInitLinearAPIWithoutLinearOpMode() {
        val api = EmptyLinearAPI()
        val opMode = EmptyOpMode()

        assertFailsWith<InitLinearAPIWithoutLinearOpMode> { api.init(opMode) }
    }
}
