package com.hatchedlabs.grocery.deeplink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TestDeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deep_link_test_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DeepLinkTestFragment.newInstance())
                .commitNow()
        }

    }

}
