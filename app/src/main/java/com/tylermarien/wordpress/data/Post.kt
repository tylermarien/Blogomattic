package com.tylermarien.wordpress.data

import com.squareup.moshi.Json

data class Post(
    @Json(name = "ID") val id: Int,
    val title: String,
    val content: String,
    val excerpt: String
)