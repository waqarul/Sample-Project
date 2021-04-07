package com.waqar.sampleproject.features.home.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.viewitem.BaseViewItem
import com.waqar.sampleproject.base.viewmodel.BaseViewModel
import com.waqar.sampleproject.base.viewmodel.Event
import com.waqar.sampleproject.core.model.AlertModel
import com.waqar.sampleproject.core.model.VehicleModel
import com.waqar.sampleproject.extentions.getFuel
import com.waqar.sampleproject.extentions.getPrice
import com.waqar.sampleproject.extentions.getTitle
import com.waqar.sampleproject.extentions.getVehicleImagesUrl
import com.waqar.sampleproject.features.home.interactor.VehicleInteractor
import com.waqar.sampleproject.features.home.viewitems.AdvertisementViewItem
import com.waqar.sampleproject.features.home.viewitems.VehicleViewItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : BaseViewModel() {
    // Private fields
    private val interactor = VehicleInteractor()
    private val randomGenerator: Random = Random()
    private val advertisementList = arrayListOf(
        R.drawable.advertisement_1,
        R.drawable.advertisement_2,
        R.drawable.advertisement_3,
        R.drawable.advertisement_4,
        R.drawable.advertisement_5
    )

    // Live data
    var viewItems = MutableLiveData<List<BaseViewItem>>()
    val isRecordFound: MutableLiveData<Boolean> = MutableLiveData()
    val showRefreshIndicator: MutableLiveData<Boolean> = MutableLiveData()

    override fun loadData(params: Bundle?) {
        fetchVehicleList()
    }

    private fun fetchVehicleList() {

        if (!isInternetConnectionAvailable) {
            showNoInternetAlert.value = Event(true)
            showLoading.value = false
            showRefreshIndicator.value = false
            return
        }

        showLoading.value = true
        val disposable = interactor.getVehicleItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ vehicleItems ->
                showLoading.value = false
                showRefreshIndicator.value = false

                if (vehicleItems.isNotEmpty()) {
                    isRecordFound.value = true
                    createViewItems(vehicleItems)
                } else {
                    isRecordFound.value = false
                }

            }, {
                showLoading.value = false
                showRefreshIndicator.value = false

                this.showAlertDialog.value = Event(
                    AlertModel(
                        title = context.getString(R.string.title_error_in_request),
                        message = context.getString(R.string.message_error_in_request),
                        positiveButtonTitle = context.getString(R.string.alert_ok_label)
                    )
                )

            })

        disposables.add(disposable)
    }

    private fun createViewItems(vehicleItems: List<VehicleModel>) {
        val itemsList = ArrayList<BaseViewItem>()
        vehicleItems.mapIndexed { index, vehicleModel ->
            itemsList.add(
                VehicleViewItem(
                    vehicleModel.getTitle(),
                    vehicleModel.getPrice(),
                    vehicleModel.getFuel(),
                    vehicleModel.getVehicleImagesUrl()
                )
            )

            // show advertisement view at 3rd position
            if (index != 0 && index % 2 == 0) {
                itemsList.add(AdvertisementViewItem(getRandomAdvertisementImage()))
            }
        }
        viewItems.value = itemsList
    }

    private fun getRandomAdvertisementImage(): Int {
        val index = randomGenerator.nextInt(advertisementList.size)
        return advertisementList[index]
    }
}