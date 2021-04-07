package com.waqar.sampleproject.base.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waqar.sampleproject.base.Domain
import com.waqar.sampleproject.configuration.ConnectionStateMonitor
import com.waqar.sampleproject.core.model.AlertModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(val context: Context = Domain.applicationContext) : ViewModel() {

    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    var isInternetAvailableLiveData = MutableLiveData<Event<Boolean>>()
    val showAlertDialog: MutableLiveData<Event<AlertModel>> = MutableLiveData()

    private val connectionStateMonitor = ConnectionStateMonitor(context)
    protected var isInternetConnectionAvailable = false
        private set

    protected val disposables = CompositeDisposable()

    var showNoInternetAlert = MutableLiveData<Event<Boolean>>()

    init {
        disposables.add(connectionStateMonitor.isInternetAvailable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isInternetConnectionAvailable =
                    connectionStateMonitor.isInternetConnectionAvailable()
            }
            .subscribe {
                isInternetConnectionAvailable = it
                isInternetAvailableLiveData.postValue(Event(it))
            })
        connectionStateMonitor.register()
    }

    abstract fun loadData(params: Bundle? = null)

    open fun onAlertDismissed(code: Int) {}

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        connectionStateMonitor.unRegister()
    }
}