package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlin.test.Test
import kotlin.test.assertFailsWith

class APITest {
    @Test
    fun testAPINotInitialized() {
        val api = object : API() {
            fun accessOpMode() = this.opMode
        }

        assertFailsWith<APINotInitialized> { api.accessOpMode() }
    }

    @Test
    fun testInitAPIMoreThanOnce() {
        val opMode = object : OpMode() {
            override fun init() {}
            override fun loop() {}
        }

        val api = object : API() {}

        // Initialize API once.
        api.init(opMode)

        assertFailsWith<InitAPIMoreThanOnce> {
            // Initialize API twice.
            api.init(opMode)
        }
    }

    @Test
    fun testIllegalLinearOpModeAccess() {
        val api = object : API() {
            fun accessLinearOpMode() = this.linearOpMode
        }

        assertFailsWith<IllegalLinearOpModeAccess> { api.accessLinearOpMode() }
    }

    @Test
    fun testInitLinearAPIWithoutLinearOpMode() {
        val opMode = object : OpMode() {
            override fun init() {}
            override fun loop() {}
        }

        val api = object : API() {
            override val isLinear = true
        }

        assertFailsWith<InitLinearAPIWithoutLinearOpMode> { api.init(opMode) }
    }
}
