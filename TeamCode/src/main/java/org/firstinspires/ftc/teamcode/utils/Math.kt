package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Vector2d
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.round

/** PI / 2 */
const val PI_2: Double = PI / 2.0

/**
 * Rounds a number to a certain number of decimal places.
 *
 * This can be useful when comparing two doubles, since floating point operations tend to cause
 * small amounts of drift.
 *
 * For example, `(3.222).roundDecimal(1) == 3.2`. We're truncating to the 1st decimal place, rounding
 * the rest down. On the other hand, `(0.9999).roundDecimal(3) == 1.0` because the 4th 9 rounds up.
 */
fun Double.roundDecimal(places: Int): Double {
    val k = 10.0.pow(places)
    return round(this * k) / k
}

/** Applies function [f] to both [Vector2d.x] and [Vector2d.y], returning the result. */
inline fun Vector2d.map(f: (Double) -> Double) = Vector2d(f(this.x), f(this.y))

/** Returns the inverse tangent of a dual number. */
fun <Param> DualNum<Param>.atan(): DualNum<Param> {
    // Please see https://github.com/BotsBurgh/BOTSBURGH-FTC-2024-25/wiki/On-dual-numbers for an
    // explanation of the code, and a guide to create your own.

    val x = this.values().toDoubleArray()
    val y = DoubleArray(x.size)

    if (y.isEmpty()) return y.toDual()

    // Constant value
    y[0] = kotlin.math.atan(x[0])
    if (y.size == 1) return y.toDual()

    // First derivative
    val firstDeriv = 1 / (x[0].squared() + 1)
    y[1] = firstDeriv * x[1]
    if (y.size == 2) return y.toDual()

    // Second derivative
    val secondDeriv = (-2 * x[0]) / (x[0].squared() + 1).squared()
    y[2] = secondDeriv * x[1] + firstDeriv * x[2]
    if (y.size == 3) return y.toDual()

    // Third derivative
    val thirdDeriv = (6 * x[0].squared() - 2) / (x[0].squared() + 1).cubed()
    y[3] = thirdDeriv * x[1] + secondDeriv * x[2] + secondDeriv * x[2] + firstDeriv * x[3]

    return y.toDual()
}

/**
 * Converts a [DoubleArray] to a [DualNum].
 *
 * This is a temporary method until
 * [road-runner#108](https://github.com/acmerobotics/road-runner/pull/108) is released.
 */
private fun <Param> DoubleArray.toDual() = DualNum<Param>(this.toList())

/** Squares a number `x^2`. */
private fun Double.squared() = this * this

/** Cubes a number `x^3`. */
private fun Double.cubed() = this * this * this
