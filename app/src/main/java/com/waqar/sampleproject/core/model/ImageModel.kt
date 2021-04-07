package com.waqar.sampleproject.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageModel(
    @Json(name = "url") val url: String
)
