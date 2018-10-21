package com.tylermarien.wordpress.data

import com.squareup.moshi.Json
import java.util.*

data class Post(
    @Json(name="ID")
    val id: Int,
    @Json(name="site_ID")
    val siteId: Int,
    val author: Author,
    val date: Date,
    val modified: Date,
    val title: String,
    @Json(name="URL")
    val url: String,
    @Json(name="short_URL")
    val shortUrl: String,
    val content: String,
    val excerpt: String,
    val slug: String,
    val giud: String
)