package com.waqar.sampleproject.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayout())

        ButterKnife.bind(this)
    }

    abstract fun getContentLayout(): Int

    open fun getContainerId(): Int {
        return 0
    }
}