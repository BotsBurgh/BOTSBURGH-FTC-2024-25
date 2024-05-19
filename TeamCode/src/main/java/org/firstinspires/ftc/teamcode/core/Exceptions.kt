package org.firstinspires.ftc.teamcode.core

import kotlin.reflect.KClass

class InitAPIMoreThanOnce(clazz: KClass<out API>) : IllegalStateException("Tried to initialize an API ${clazz.simpleName} more than once.")

class InitLinearAPIWithoutLinearOpMode(clazz: KClass<out API>) : IllegalArgumentException(
    """
    Tried to initialize a linear API ${clazz.simpleName} without a `LinearOpMode`.
    Please make sure that your opmode extends `LinearOpMode`.
    Also please check the ${clazz.simpleName} has not accidentally set `isLinear` to true.
    """.trimIndent(),
)
