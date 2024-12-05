package org.firstinspires.ftc.teamcode.core.logging

import org.threeten.bp.Instant

/**
 * A simple [Logger] used in unit tests.
 */
class UnitTestLogger(tag: String) : Logger(tag) {
    override fun log(level: Level, msg: String) {
        println("[${Instant.now()} ${level.name} ${this.tag}] $msg")
    }
}
