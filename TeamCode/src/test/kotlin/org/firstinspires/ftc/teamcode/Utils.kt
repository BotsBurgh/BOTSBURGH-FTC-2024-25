package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.core.Resettable

/**
 * Calls [Resettable.resetAll], then runs [func].
 *
 * This should be used when testing APIs that require state.
 */
fun withReset(func: () -> Unit) {
    Resettable.resetAll()
    func()
}
