package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.api.input.Button
import org.firstinspires.ftc.teamcode.api.input.GamepadEx
import org.firstinspires.ftc.teamcode.api.input.Input
import org.firstinspires.ftc.teamcode.withReset
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class InputTest {
    private class EmptyOpMode : OpMode() {
        init {
            this.gamepad1 = Gamepad()
            this.gamepad2 = Gamepad()
        }

        override fun init() {}

        override fun loop() {}
    }

    @Test
    fun testPressed() {
        val opMode = EmptyOpMode()
        val gamepadEx = GamepadEx(GamepadEx.GamepadNumber.Gamepad1)

        // Press X and update.
        opMode.gamepad1.x = true
        gamepadEx.update(opMode)

        assertTrue(gamepadEx.pressed(Button.X))

        // Hold X and update.
        gamepadEx.update(opMode)

        assertTrue(gamepadEx.pressed(Button.X))

        // Release X and update.
        opMode.gamepad1.x = false
        gamepadEx.update(opMode)

        assertFalse(gamepadEx.pressed(Button.X))
    }

    @Test
    fun testJustPressed() {
        val opMode = EmptyOpMode()
        val gamepadEx = GamepadEx(GamepadEx.GamepadNumber.Gamepad1)

        // Press Y and update.
        opMode.gamepad1.y = true
        gamepadEx.update(opMode)

        assertTrue(gamepadEx.justPressed(Button.Y))

        // Hold Y and update.
        gamepadEx.update(opMode)

        assertFalse(gamepadEx.justPressed(Button.Y))

        // Release Y and update.
        opMode.gamepad1.y = false
        gamepadEx.update(opMode)

        assertFalse(gamepadEx.justPressed(Button.Y))
    }

    @Test
    fun testJustReleased() {
        val opMode = EmptyOpMode()
        val gamepadEx = GamepadEx(GamepadEx.GamepadNumber.Gamepad1)

        // Press A and update.
        opMode.gamepad1.a = true
        gamepadEx.update(opMode)

        assertFalse(gamepadEx.justReleased(Button.A))

        // Release A and update.
        opMode.gamepad1.a = false
        gamepadEx.update(opMode)

        assertTrue(gamepadEx.justReleased(Button.A))

        // Keep A released and update.
        gamepadEx.update(opMode)

        assertFalse(gamepadEx.justReleased(Button.A))
    }

    @Test
    fun testGamepad2() {
        val opMode = EmptyOpMode()
        val gamepadEx1 = GamepadEx(GamepadEx.GamepadNumber.Gamepad1)
        val gamepadEx2 = GamepadEx(GamepadEx.GamepadNumber.Gamepad2)

        // Press B on Gamepad 1.
        opMode.gamepad1.b = true
        gamepadEx1.update(opMode)
        gamepadEx2.update(opMode)

        assertTrue(gamepadEx1.pressed(Button.B))
        assertFalse(gamepadEx2.pressed(Button.B))

        // Press B on Gamepad 2, hold B on Gamepad 1.
        opMode.gamepad2.b = true
        gamepadEx1.update(opMode)
        gamepadEx2.update(opMode)

        assertTrue(gamepadEx1.pressed(Button.B))
        assertTrue(gamepadEx2.pressed(Button.B))

        // Release B on both gamepads.
        opMode.gamepad1.b = false
        opMode.gamepad2.b = false
        gamepadEx1.update(opMode)
        gamepadEx2.update(opMode)

        assertFalse(gamepadEx1.pressed(Button.B))
        assertFalse(gamepadEx2.pressed(Button.B))
    }

    @Test
    fun testAPIShortcuts() =
        withReset {
            val opMode = EmptyOpMode()
            Input.init(opMode)

            opMode.gamepad1.dpad_up = true
            Input.update()

            assertTrue(Input.pressed(Button.DPadUp))
            assertTrue(Input.justPressed(Button.DPadUp))

            opMode.gamepad1.dpad_up = false
            Input.update()

            assertFalse(Input.pressed(Button.DPadUp))
            assertTrue(Input.justReleased(Button.DPadUp))
        }

    @Test
    fun testAPIGamepads() =
        withReset {
            val opMode = EmptyOpMode()
            Input.init(opMode)

            val gamepad1 = Input.gamepad1
            val gamepad2 = Input.gamepad2

            opMode.gamepad1.left_bumper = true
            opMode.gamepad2.right_bumper = true
            Input.update()

            assertTrue(gamepad1.pressed(Button.LeftBumper))
            assertTrue(gamepad2.pressed(Button.RightBumper))
        }

    @Test
    fun testButtonPressed() {
        val gamepad = Gamepad()
        val button = Button.LeftStickButton

        gamepad.left_stick_button = true

        assertTrue(button.pressed(gamepad))
    }
}
