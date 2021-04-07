package com.waqar.sampleproject.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SellerModel(
    @Json(name = "type") val type: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "city") val city: String
)