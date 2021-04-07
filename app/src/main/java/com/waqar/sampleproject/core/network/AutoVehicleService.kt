package com.waqar.sampleproject.core.network

import com.waqar.sampleproject.constants.ApiConstants
import com.waqar.sampleproject.core.model.VehicleModel
import io.reactivex.Observable
import retrofit2.http.GET

interface AutoVehicleService {
    @GET(ApiConstants.GET_VEHICLE_ITEMS)
    fun getVehicleItems(): Observable<List<VehicleModel>>
}