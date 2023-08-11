package com.example.hackathon.network

import android.util.Log
import com.example.hackathon.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {

    const val TAG = "API체크"

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    val httpClient =  HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging){
            logger = object : Logger { //logger 자체가 인터페이스여서
                override fun log(message: String) {
                    Log.d(TAG, "api log: &message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout){
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }

        defaultRequest { //기본 request
            headers.append("Accept", "application/json")
            headers.append("Content-Type", "application/json")
            headers.append("apikey", BuildConfig.SUPERBASE_KEY)
        }
    }
}