package com.leti.monitoring.backend.dao;

import org.jetbrains.exposed.sql.SizedIterable
import com.leti.monitoring.backend.model.Step
import com.leti.monitoring.backend.model.StepDtoInsert

class StepDao {

    fun insert(item: StepDtoInsert): Step {
        return Step.new {
            stepsCount = item.stepsCount
            deviceId = item.deviceId
        }
    }

    fun getAll(): SizedIterable<Step> {
        return Step.all()
    }
}
