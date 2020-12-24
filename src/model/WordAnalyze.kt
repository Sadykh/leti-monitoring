package com.leti.monitoring.backend.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import java.time.Instant

object WordAnalyzes : IntIdTable("_5_word_analyze") {

    var requiredWorld = WordAnalyzes.varchar("required_world", length = 255)
    var receivedWorld = WordAnalyzes.varchar("received_world", length = 255)
    var isExact = WordAnalyzes.bool("is_exact")
    var hasSpace = WordAnalyzes.bool("has_space")
    var deviceId = WordAnalyzes.varchar("device_id", length = 255)

    var createdAt = WordAnalyzes.long("createdAt").default(Instant.now().epochSecond)
    var updatedAt = WordAnalyzes.long("updatedAt").default(Instant.now().epochSecond)
}


class WordAnalyze(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WordAnalyze>(WordAnalyzes)

    var requiredWorld by WordAnalyzes.requiredWorld
    var receivedWorld by WordAnalyzes.receivedWorld
    var isExact by WordAnalyzes.isExact
    var hasSpace by WordAnalyzes.hasSpace
    var deviceId by WordAnalyzes.deviceId
    var createdAt by WordAnalyzes.createdAt
    var updatedAt by WordAnalyzes.updatedAt

    fun dto(): WordAnalyzeDto {
        return WordAnalyzeDto(
            this.id.value,
            this.requiredWorld,
            this.receivedWorld,
            this.isExact,
            this.hasSpace,
            this.deviceId,
            this.createdAt,
            this.updatedAt
        )
    }
}

data class WordAnalyzeInsert(
    val requiredWorld: String,
    val receivedWorld: String,
    val isExact: Boolean,
    val hasSpace: Boolean,
    val deviceId: String
)

data class WordAnalyzeDto(
    var id: Int,
    val requiredWorld: String,
    val receivedWorld: String,
    val isExact: Boolean,
    val hasSpace: Boolean,
    val deviceId: String,
    var createdAt: Long, var updatedAt: Long
)


