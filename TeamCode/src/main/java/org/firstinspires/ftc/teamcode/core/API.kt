package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.utils.isNotNull

abstract class API {
    private var uninitializedOpMode: OpMode? by Resettable { null }

    // Make sure to error about calling super.init() too.
    protected val opMode: OpMode
        get() = this.uninitializedOpMode ?: throw APINotInitialized(this::class)

    protected val linearOpMode: LinearOpMode
        get() {
            if (!this.isLinear) {
                throw IllegalLinearOpModeAccess(this::class)
            }

            return this.opMode as LinearOpMode
        }

    open val isLinear: Boolean = false
    open val dependencies: Set<API> = emptySet()

    open fun init(opMode: OpMode) {
        // You can only initialize an API once without tearing it down.
        if (this.uninitializedOpMode.isNotNull()) {
            throw InitAPIMoreThanOnce(this::class)
        }

        // A LinearOpMode must be passed into this function if `isLinear` is true.
        if (this.isLinear && opMode !is LinearOpMode) {
            throw InitLinearAPIWithoutLinearOpMode(this::class)
        }

        this.uninitializedOpMode = opMode

        Dependencies.registerAPI(this)
    }
}
