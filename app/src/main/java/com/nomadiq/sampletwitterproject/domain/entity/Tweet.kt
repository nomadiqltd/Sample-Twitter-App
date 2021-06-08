package com.nomadiq.sampletwitterproject.domain.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Gets tweet information from the API Retrofit service
 */
@Parcelize
data class Tweet(
    @Json(name = "full_text") val fullText: String,
    @Json(name = "id") val tweetId: Long,
    @Json(name = "created_at") val createdAt: String
) : Parcelable
