package com.waqar.sampleproject.features.home.interactor

import com.waqar.sampleproject.base.interactor.BaseInteractor
import com.waqar.sampleproject.core.model.VehicleModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class VehicleInteractor : BaseInteractor() {

    fun getVehicleItems(): Observable<List<VehicleModel>> {
        return Observable.create { emitter ->

            apiClient.getVehicleItems()
                .subscribeOn(Schedulers.io())
                .subscribe({ vehicleItems ->

                    emitter.onNext(vehicleItems)
                    emitter.onComplete()

                }, { error ->
                    emitter.onError(error)
                })
        }
    }
}