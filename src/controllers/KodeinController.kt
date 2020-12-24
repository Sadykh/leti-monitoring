package com.leti.monitoring.backend.controllers

import io.ktor.routing.Routing
import org.kodein.di.*
import org.kodein.di.generic.instance
import io.ktor.application.*

abstract class KodeinController(override val kodein: Kodein) : KodeinAware {
    /**
     * Injected dependency with the current [Application].
     */
    val application: Application by instance()

    /**
     * Method that subtypes must override to register the handled [Routing] routes.
     */
    abstract fun Routing.registerRoutes()
}
