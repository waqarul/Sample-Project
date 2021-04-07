package com.waqar.sampleproject.extentions

import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.Domain
import com.waqar.sampleproject.core.model.VehicleModel
import java.text.NumberFormat
import java.util.*

fun VehicleModel.getTitle(): String {
    return "$make $model"
}

fun VehicleModel.getPrice(): String {
    return String.format(
        Domain.applicationContext.getString(R.string.vehicle_price),
        getFormattedPrice(price)
    )
}

fun VehicleModel.getFuel(): String {
    return String.format(Domain.applicationContext.getString(R.string.vehicle_fuel), fuel)
}

fun VehicleModel.getVehicleImagesUrl(): List<String>? {
    return vehicleImages?.map { it.url }
}

private fun getFormattedPrice(price: Long): String {
    val nf = NumberFormat.getNumberInstance(Locale.GERMANY)

    return try {
        nf.format(price)
    } catch (e: IllegalArgumentException) {
        "0"
    }
}