package com.example.hackathon.network.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest (
    val email: String,
    val password: String
)