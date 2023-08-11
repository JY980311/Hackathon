package com.example.hackathon.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long,
    val title: String,
    val content: String,

    @SerialName("user_id") // @SerialName을 통해 json의 key값과 변수명을 매칭시켜준다.
    val userID: String? = null,

    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class PostRequest (
    val title: String,
    val content: String? = null,

    @SerialName("user_id")
    val userID: String? = null
)



