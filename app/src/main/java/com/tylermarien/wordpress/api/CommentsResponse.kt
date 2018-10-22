package com.tylermarien.wordpress.api

import com.squareup.moshi.Json
import com.tylermarien.wordpress.data.Comment

data class CommentsResponse(
    val found: Int,
    @Json(name="site_ID")
    val siteId: Int,
    val comments: List<Comment>
)