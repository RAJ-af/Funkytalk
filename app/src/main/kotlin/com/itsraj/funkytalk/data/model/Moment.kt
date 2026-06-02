package com.itsraj.funkytalk.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Moment(
    val id: Long = 0,
    val user_id: String = "",
    val content: String = "",
    val image_urls: List<String>? = null,
    val likes_count: Int = 0,
    val comments_count: Int = 0,
    val created_at: String? = null
)
