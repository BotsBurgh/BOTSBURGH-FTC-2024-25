package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.Vector2dDual
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * Represents a coordinate in the
 * [polar coordinate system](https://en.wikipedia.org/wiki/Polar_coordinate_system).
 *
 * [theta] is the relative angle from 0 in radians on the unit circle. (A theta of 0 points to the
 * right, not up.) [radius] is the distance from the center, used to represent strength or
 * magnitude.
 *
 * For a alternative to this that uses the cartesian coordinate system (x, y), see [Vector2d].
 */
data class Polar2d(val theta: Double, val radius: Double) {
    companion object {
        /** Converts cartesian coordinates into polar coordinates. */
        fun fromCartesian(
            x: Double,
            y: Double,
        ): Polar2d {
            // Inverse tangent to find the angle, except it special-cases specific circumstances.
            val theta = atan2(y, x)

            // Calculate the hypotenuse (distance) using sqrt(x^2 + y^2).
            val radius = hypot(x, y)

            return Polar2d(theta, radius)
        }

        /** Converts cartesian coordinates into polar coordinates. */
        fun fromCartesian(cartesian: Vector2d): Polar2d = fromCartesian(cartesian.x, cartesian.y)
    }

    /** Returns the cartesian form of these polar coordinates. */
    fun toCartesian() =
        Vector2d(
            this.radius * cos(this.theta),
            this.radius * sin(this.theta),
        )
}

data class Polar2dDual<Param>(val theta: DualNum<Param>, val radius: DualNum<Param>) {
    companion object {
        fun <Param> fromCartesian(
            x: DualNum<Param>,
            y: DualNum<Param>,
        ): Polar2dDual<Param> {
            val theta = y.atan2(x)
            val radius = (x * x + y * y).sqrt()
            return Polar2dDual(theta, radius)
        }
    }

    fun toCartesian() =
        Vector2dDual(
            this.radius * this.theta.cos(),
            this.radius * this.theta.sin(),
        )
}
