package com.tylermarien.blogomattic.data

import com.squareup.moshi.Json
import java.util.*

data class Comment(
    @Json(name="ID")
    val id: Int,
    val author: Author,
    val date: Date,
    @Json(name="URL")
    val url: String,
    @Json(name="short_URL")
    val shortUrl: String,
    val content: String,
    @Json(name="like_count")
    val likeCount: Int
) {
    data class Author(
        @Json(name="ID")
        val id: Int,
        val name: String
    )
}