package com.leti.monitoring.backend.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import java.time.Instant

object Steps : IntIdTable("_5_step") {

    var stepsCount = Steps.integer("steps_count")
    var deviceId = Steps.varchar("device_id", length = 255)

    var createdAt = Steps.long("createdAt").default(Instant.now().epochSecond)
    var updatedAt = Steps.long("updatedAt").default(Instant.now().epochSecond)
}


class Step(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Step>(Steps)

    var stepsCount by Steps.stepsCount
    var deviceId by Steps.deviceId
    var createdAt by Steps.createdAt
    var updatedAt by Steps.updatedAt

    fun dto(): StepDto {
        return StepDto(
            this.id.value,
            this.stepsCount,
            this.deviceId,
            this.createdAt,
            this.updatedAt
        )
    }
}

data class StepDtoInsert(
    val stepsCount: Int,
    val deviceId: String
)

data class StepDto(
    var id: Int,
    var stepsCount: Int,
    var deviceId: String,
    var createdAt: Long, var updatedAt: Long
)


