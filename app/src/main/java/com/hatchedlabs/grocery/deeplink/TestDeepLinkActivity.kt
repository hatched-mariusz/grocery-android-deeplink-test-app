package com.hatchedlabs.grocery.deeplink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hatchedlabs.grocery.deeplink.ui.main.DeepLinkTestFragment

class TestDeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_deep_link_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DeepLinkTestFragment.newInstance())
                .commitNow()
        }

    }

}
