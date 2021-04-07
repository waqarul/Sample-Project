package com.waqar.sampleproject.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehicleModel(
    @Json(name = "id") val id: Long,
    @Json(name = "make") val make: String,
    @Json(name = "model") val model: String,
    @Json(name = "price") val price: Long,
    @Json(name = "firstRegistration") val firstRegistration: String? = null,
    @Json(name = "mileage") val mileage: Long,
    @Json(name = "fuel") val fuel: String,
    @Json(name = "images") val vehicleImages: List<ImageModel>? = null,
    @Json(name = "description") val description: String,
    @Json(name = "modelline") val modelLine: String? = null,
    @Json(name = "seller") val seller: SellerModel? = null,
    @Json(name = "colour") val colour: String? = null
)