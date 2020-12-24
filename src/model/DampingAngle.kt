package com.leti.monitoring.backend.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import java.time.Instant

object DampingAngles : IntIdTable("_5_damping_angle") {
    var angle = DampingAngles.integer("angle")
    var deviceId = DampingAngles.varchar("device_id", length = 255)

    var createdAt = DampingAngles.long("createdAt").default(Instant.now().epochSecond)
    var updatedAt = DampingAngles.long("updatedAt").default(Instant.now().epochSecond)
}

class DampingAngle(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DampingAngle>(DampingAngles)

    var angle by DampingAngles.angle
    var deviceId by DampingAngles.deviceId
    var createdAt by DampingAngles.createdAt
    var updatedAt by DampingAngles.updatedAt

    fun dto(): DampingAnglesDto {
        return DampingAnglesDto(
            this.id.value,
            this.angle,
            this.deviceId,
            this.createdAt,
            this.updatedAt
        )
    }
}

data class DampingAngleDtoInsert(
    val angle: Int,
    val deviceId: String
)

data class DampingAnglesDto(
    var id: Int,
    var angle: Int,
    var deviceId: String,
    var createdAt: Long,
    var updatedAt: Long
)


