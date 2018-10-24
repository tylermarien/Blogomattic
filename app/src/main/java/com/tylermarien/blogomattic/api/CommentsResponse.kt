package com.tylermarien.blogomattic.api

import com.squareup.moshi.Json
import com.tylermarien.blogomattic.data.Comment

data class CommentsResponse(
    val found: Int,
    @Json(name="site_ID")
    val siteId: Int,
    val comments: List<Comment>
)