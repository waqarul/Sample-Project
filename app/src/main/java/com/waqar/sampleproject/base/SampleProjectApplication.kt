package com.waqar.sampleproject.base

import android.app.Application

class SampleProjectApplication : Application() {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()

        initialize()
    }

    private fun initialize() {
        instance = this

        Domain.integrateWith(this)
    }
}