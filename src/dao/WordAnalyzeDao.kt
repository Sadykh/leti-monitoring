package com.leti.monitoring.backend.dao;

import org.jetbrains.exposed.sql.SizedIterable
import com.leti.monitoring.backend.model.WordAnalyze
import com.leti.monitoring.backend.model.WordAnalyzeInsert

class WordAnalyzeDao {

    fun insert(item: WordAnalyzeInsert): WordAnalyze {
        return WordAnalyze.new {
            requiredWorld = item.requiredWorld
            receivedWorld = item.receivedWorld
            isExact = item.isExact
            hasSpace = item.hasSpace
            deviceId = item.deviceId
        }
    }

    fun getAll(): SizedIterable<WordAnalyze> {
        return WordAnalyze.all()
    }
}
