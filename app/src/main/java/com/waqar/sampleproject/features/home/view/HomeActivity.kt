package com.waqar.sampleproject.features.home.view

import android.os.Bundle
import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.view.BaseActivity
import com.waqar.sampleproject.features.home.fragments.HomeFragment

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show home fragment as default view
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(getContainerId(), HomeFragment()).commit()
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun getContainerId(): Int {
        return R.id.content_container;
    }
}