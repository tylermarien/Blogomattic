package com.tylermarien.wordpress.data

import com.squareup.moshi.Json

data class Discussion(
    @Json(name="comments_open")
    val commentsOpen: Boolean,
    @Json(name="comment_status")
    val commentStatus: String,
    @Json(name="pings_open")
    val pingsOpen: Boolean,
    @Json(name="ping_status")
    val pingStatus: String,
    @Json(name="comment_count")
    val commentsCount: Int
)