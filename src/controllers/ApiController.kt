package com.leti.monitoring.backend.controllers

import com.leti.monitoring.backend.dao.DampingAngleDao
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import org.kodein.di.generic.instance
import org.kodein.di.*
import com.leti.monitoring.backend.dao.StepDao
import com.leti.monitoring.backend.dao.WordAnalyzeDao
import com.leti.monitoring.backend.model.*

class ApiController(kodein: Kodein) : KodeinController(kodein) {
    private val stepDao: StepDao by kodein.instance()
    private val dampingAngleDao: DampingAngleDao by kodein.instance()
    private val wordAnalyzeDao: WordAnalyzeDao by kodein.instance()

    data class ValidateResponse(
        val error: Any? = null
    )


    override fun Routing.registerRoutes() {

        route("/api") {
            get("/steps") {
                call.respond(
                    try {
                        transaction {
                            val stepsCount = call.parameters["steps_count"] ?: throw Exception("steps_count required")
                            val deviceId = call.parameters["device_id"] ?: throw Exception("device_id required")
                            val result = stepDao.insert(StepDtoInsert(stepsCount.toInt(), deviceId))
                            result.dto()
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }
                )
            }

            get("/steps/all") {
                call.respond(
                    try {
                        transaction {
                            stepDao.getAll().map(Step::dto)
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }
                )
            }

            get("/damping-angle") {
                call.respond(
                    try {
                        val angle = call.parameters["angle"] ?: throw Exception("angle required")
                        val deviceId = call.parameters["device_id"] ?: throw Exception("device_id required")
                        transaction {
                            val result = dampingAngleDao.insert(DampingAngleDtoInsert(angle.toInt(), deviceId))
                            result.dto()
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }
                )
            }

            get("/damping-angle/all") {
                call.respond(
                    try {
                        transaction {
                            dampingAngleDao.getAll().map(DampingAngle::dto)
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }
                )
            }

            get("/word-analyze/all") {
                call.respond(
                    try {
                        transaction {
                            wordAnalyzeDao.getAll().map(WordAnalyze::dto)
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }
                )
            }

            get("/word-analyze") {
                call.respond(
                    try {
                        val requiredWorld =
                            call.parameters["required_world"] ?: throw Exception("required_world required")
                        val receivedWorld =
                            call.parameters["received_world"] ?: throw Exception("received_world required")
                        val isExact = call.parameters["is_exact"] ?: throw Exception("is_exact required")
                        val hasSpace = call.parameters["has_space"] ?: throw Exception("has_space required")
                        val deviceId = call.parameters["device_id"] ?: throw Exception("device_id required")
                        transaction {
                            val result = wordAnalyzeDao.insert(
                                WordAnalyzeInsert(
                                    requiredWorld,
                                    receivedWorld,
                                    isExact.toBoolean(),
                                    hasSpace.toBoolean(),
                                    deviceId
                                )
                            )
                            result.dto()
                        }
                    } catch (e: java.lang.Exception) {
                        ValidateResponse(
                            error = e.message
                        )
                    }

                )
            }
        }
    }
}

