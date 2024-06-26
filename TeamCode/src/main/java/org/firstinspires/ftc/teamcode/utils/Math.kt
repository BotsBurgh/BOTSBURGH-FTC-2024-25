package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.Vector2d
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.round

/**
 * Truncates a number a certain number of decimal places.
 *
 * This can be useful when comparing two doubles, since floating point operations tend to cause
 * small amounts of drift.
 *
 * For example, `(3.222).truncate(1) == 3.2`. We're truncating to the 1st decimal place, rounding
 * the rest down. On the other hand, `(0.9999).truncate(3) == 1.0` because the 4th 9 rounds up.
 */
fun Double.truncate(places: Int): Double {
    val k = 10.0.pow(places)
    return round(this * k) / k
}

/** Returns the absolute value of a [Vector2d]. */
fun Vector2d.abs() = Vector2d(this.x.absoluteValue, this.y.absoluteValue)

/** Applies function [f] to both [Vector2d.x] and [Vector2d.y], returning the result. */
inline fun Vector2d.map(f: (Double) -> Double) = Vector2d(f(this.x), f(this.y))
