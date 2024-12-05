package org.firstinspires.ftc.teamcode.core.logging

import android.util.Log

enum class Level(val priority: Int) {
    Debug(Log.DEBUG),
    Info(Log.INFO),
    Warn(Log.WARN),
    Error(Log.ERROR),
}
