package com.leti.monitoring.backend

import java.lang.Exception

object EnvManager {

    fun get(keyName: String): String {
        try {
            return System.getenv(keyName)
        } catch (error: Exception) {
            throw Exception("Key $keyName not exist in environment. Please, check .env file")
        }
    }
}
