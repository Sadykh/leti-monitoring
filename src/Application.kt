package com.leti.monitoring.backend

import io.ktor.application.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import org.kodein.di.*
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

import com.leti.monitoring.backend.controllers.ApiController
import java.lang.Exception
import java.text.DateFormat
import com.leti.monitoring.backend.controllers.KodeinController
import com.leti.monitoring.backend.dao.DampingAngleDao
import com.leti.monitoring.backend.dao.StepDao
import com.leti.monitoring.backend.dao.WordAnalyzeDao
import com.leti.monitoring.backend.model.*
import org.jetbrains.exposed.sql.SchemaUtils

suspend fun connectDB() {
    try {
        val databaseHost = EnvManager.get("POSTGRES_HOST")
        val database = EnvManager.get("POSTGRES_DB")
        val port = EnvManager.get("POSTGRES_PORT").toInt()
        Database.connect(
            url = "jdbc:postgresql://$databaseHost:$port/$database",
            driver = "org.postgresql.Driver",
            user = EnvManager.get("POSTGRES_USER"),
            password = EnvManager.get("POSTGRES_PASSWORD")
        )
    } catch (error: Exception) {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;LOCK_TIMEOUT=100000", driver = "org.h2.Driver")
    }

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)
        // register table
//        println("Creating tables...")
//        SchemaUtils.createMissingTablesAndColumns(Steps)
//        SchemaUtils.createMissingTablesAndColumns(DampingAngles)
//        SchemaUtils.createMissingTablesAndColumns(WordAnalyzes)
    }
}


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        kodeinApplication() { application -> }
    }.start(wait = true)

}

inline fun <reified T : Any> Kodein.MainBuilder.bindOverrideSingleton(crossinline callback: (Kodein) -> T) {
    bind<T>(overrides = true) with singleton { callback(this@singleton.kodein) }
}

fun Application.kodeinApplication(
    kodeinMapper: Kodein.MainBuilder.(Application) -> Unit = {}
) {
    val application = this
    val kodein = Kodein {
        bind<StepDao>() with singleton { StepDao() }
        bind<DampingAngleDao>() with singleton { DampingAngleDao() }
        bind<WordAnalyzeDao>() with singleton { WordAnalyzeDao() }
        bind<ApiController>() with singleton { ApiController(kodein) }

        bind<Application>() with instance(application)
        kodeinMapper(this, application)
    }

    /**
     * Detects all the registered [KodeinController] and registers its routes.
     */
    routing {
        for (bind in kodein.container.tree.bindings) {
            val bindClass = bind.key.type.jvmType as? Class<*>?
            if (bindClass != null && KodeinController::class.java.isAssignableFrom(bindClass)) {
                val res by kodein.Instance(bind.key.type)
                println("Registering '$res' routes...")
                (res as KodeinController).apply { registerRoutes() }
            }
        }
    }

    application.apply {
        install(DefaultHeaders)

        install(ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter())
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
            gson {
                setDateFormat(DateFormat.LONG)
                setPrettyPrinting()
            }
        }

        // create db sync
        runBlocking {
            connectDB()
        }
    }
}
