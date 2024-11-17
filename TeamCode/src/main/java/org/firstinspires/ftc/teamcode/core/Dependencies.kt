package org.firstinspires.ftc.teamcode.core

/**
 * A listener that ensures API dependencies are properly initialized when the start button is
 * pressed.
 */
object Dependencies {
    internal val initializedAPIs: MutableSet<API> by Resettable { mutableSetOf() }

    /**
     * Registers an API as initialized.
     *
     * This is called within the super implementation of [API.init], and should never be called
     * manually.
     */
    internal fun registerAPI(api: API) {
        this.initializedAPIs.add(api)
    }

    fun checkDependencies() {
        for (api in this.initializedAPIs) {
            for (dep in api.dependencies) {
                if (!this.initializedAPIs.contains(dep)) {
                    throw MissingDependency(api::class, dep::class, this.initializedAPIs)
                }
            }
        }
    }
}
