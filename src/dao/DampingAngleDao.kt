package com.leti.monitoring.backend.dao;

import com.leti.monitoring.backend.model.DampingAngle
import com.leti.monitoring.backend.model.DampingAngleDtoInsert
import org.jetbrains.exposed.sql.SizedIterable

class DampingAngleDao {
    fun insert(item: DampingAngleDtoInsert): DampingAngle {
        return DampingAngle.new {
            angle = item.angle
            deviceId = item.deviceId
        }
    }

    fun getAll(): SizedIterable<DampingAngle> {
        return DampingAngle.all()
    }
}
