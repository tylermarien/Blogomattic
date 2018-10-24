package com.tylermarien.blogomattic.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable