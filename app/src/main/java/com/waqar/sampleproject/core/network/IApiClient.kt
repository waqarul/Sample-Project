package com.waqar.sampleproject.core.network

import com.waqar.sampleproject.core.model.VehicleModel
import io.reactivex.Observable

interface IApiClient {
    fun getVehicleItems(): Observable<List<VehicleModel>>
}