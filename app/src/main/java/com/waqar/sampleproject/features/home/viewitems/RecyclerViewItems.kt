package com.waqar.sampleproject.features.home.viewitems

import androidx.annotation.DrawableRes
import com.waqar.sampleproject.base.viewitem.BaseViewItem

class VehicleViewItem(
    val title: String,
    val price: String,
    val fuel: String,
    val vehicleImagesUrl: List<String>?
) : BaseViewItem()

class AdvertisementViewItem(
    @DrawableRes
    val advertisementImage: Int
) : BaseViewItem()