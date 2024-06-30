package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.core.ResetListener

/**
 * Calls [ResetListener.resetAll], then runs [func].
 *
 * This should be used when testing APIs that require state.
 */
fun withReset(func: () -> Unit) {
    ResetListener.resetAll()
    func()
}
