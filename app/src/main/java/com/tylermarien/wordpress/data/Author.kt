package com.tylermarien.wordpress.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    @Json(name="ID")
    val id: Int,
    val login: String,
//    val email: String?,
    val name: String,
    @Json(name="first_name")
    val firstName: String,
    @Json(name="last_name")
    val lastName: String,
    @Json(name="nice_name")
    val niceName: String,
    @Json(name="URL")
    val url: String,
    @Json(name="avatar_URL")
    val avatarUrl: String,
    @Json(name="profile_URL")
    val profileUrl: String,
    @Json(name="site_ID")
    val siteId: Int
): Parcelable