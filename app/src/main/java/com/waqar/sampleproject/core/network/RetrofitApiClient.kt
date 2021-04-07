package com.waqar.sampleproject.core.network

import com.waqar.sampleproject.BuildConfig
import com.waqar.sampleproject.constants.ApiConstants
import com.waqar.sampleproject.core.model.VehicleModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitApiClient : IApiClient {

    private val autoScoutService by lazy {
        getRetrofit(ApiConstants.BASE_API_URL).create(
            AutoScoutService::class.java
        )
    }

    override fun getVehicleItems(): Observable<List<VehicleModel>> {
        return autoScoutService.getVehicleItems()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getHttpClient(): OkHttpClient {

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient().newBuilder()
                .readTimeout(ApiConstants.READ_TIME_OUT_DELAY, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.CONNECT_TIME_OUT_DELAY, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }

        return OkHttpClient().newBuilder()
            .readTimeout(ApiConstants.READ_TIME_OUT_DELAY, TimeUnit.SECONDS)
            .connectTimeout(ApiConstants.CONNECT_TIME_OUT_DELAY, TimeUnit.SECONDS)
            .addInterceptor { chain -> chain.proceed(chain.request()) }
            .build()
    }
}