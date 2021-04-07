package com.waqar.sampleproject.base.interactor

import com.waqar.sampleproject.core.network.IApiClient
import com.waqar.sampleproject.core.network.RetrofitApiClient

abstract class BaseInteractor {
    protected val apiClient: IApiClient = RetrofitApiClient()
}