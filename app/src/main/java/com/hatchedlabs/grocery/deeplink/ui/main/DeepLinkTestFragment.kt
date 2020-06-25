package com.hatchedlabs.grocery.deeplink.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatchedlabs.grocery.deeplink.R
import kotlinx.android.synthetic.main.deep_link_test_fragment.*

class DeepLinkTestFragment : Fragment(), OnExampleClickListener {

    companion object {
        fun newInstance() = DeepLinkTestFragment()
        const val SCHEMA_PROD = "gianteaglegrocery" // or for staging "staginggianteaglegrocery"
        const val HOST_PROD = "gianteaglegrocery.page.link" // or for staging "hatched.page.link"
        const val SCHEMA_STAGING = "staginggianteaglegrocery" // or for staging "staginggianteaglegrocery"
        const val HOST_STAGING = "hatched.page.link" // or for staging "hatched.page.link"
    }
    
    private lateinit var viewModel: DeepLinkTestViewModel
    val examplesAdapter = ExamplesAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.deep_link_test_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeepLinkTestViewModel::class.java)
        examples_list.layoutManager = LinearLayoutManager(context)
        examples_list.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        textInputLayout.editText?.setText("${getCurrentSchema()}://${getCurrentHost()}/main/search")
        examples_list.adapter = examplesAdapter
        prodswitch.setOnCheckedChangeListener { buttonView, isChecked ->
            examplesAdapter.setData(loadExamples())
            textInputLayout.editText?.setText("${getCurrentSchema()}://${getCurrentHost()}/main/search")
        }
        examplesAdapter.setData(loadExamples())
        run_button.setOnClickListener {
            val navIntent = Intent()
            navIntent.action = Intent.ACTION_VIEW
            navIntent.data = Uri.parse(url_edit_text.text.toString())
            try {
                startActivityForResult(navIntent, 1011)
            } catch (e: Throwable) {
                Toast.makeText(context, "error ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadExamples(): List<LinkExample> {
        return listOf(
            LinkExample("Run Grocery app by http", "https://${getCurrentHost()}/shop"),
            LinkExample("Run Grocery installed app ", "${getCurrentSchema()}://${getCurrentHost()}/main/search"),
            LinkExample("Go to search screen", "${getCurrentSchema()}://${getCurrentHost()}/main/search?query=pizza"),
            LinkExample("search in category screen", "${getCurrentSchema()}://${getCurrentHost()}/main/search?category=Prepared%20Foods&query=three%20cheese%20pizza"),
//         TODO   LinkExample("Go to search screen with selected store", "${SCHEMA}://${HOST}/main/search?selectedStore=72&fulfillmentMethod=pickup"),
            LinkExample(
                "Show product",
                "${getCurrentSchema()}://${getCurrentHost()}/product/item?sku=00070470003139&storeCode=72"
            ),
            LinkExample("Change to store", "${getCurrentSchema()}://${getCurrentHost()}/home/store?slug=westerville-healthcare"),
            LinkExample("Show user cart", "${getCurrentSchema()}://${getCurrentHost()}/main/cart"),
            LinkExample("Show user order confirmation", "${getCurrentSchema()}://${getCurrentHost()}/confirm/order?id=699KF66D"),
            LinkExample("Show order history", "${getCurrentSchema()}://${getCurrentHost()}/orders"),
            LinkExample("Show user past order", "${getCurrentSchema()}://${getCurrentHost()}/order/detail?id=699KF66D"),
            LinkExample("Show user quick list", "${getCurrentSchema()}://${getCurrentHost()}/main/quicklist"),
            LinkExample("Show curated list", "${getCurrentSchema()}://${getCurrentHost()}/shop/curatedList?id=2"),
            LinkExample("Show user account", "${getCurrentSchema()}://${getCurrentHost()}/main/account"),
            LinkExample("Change user phone number", "${getCurrentSchema()}://${getCurrentHost()}/enterphone"),
            LinkExample("Change user time slot", "${getCurrentSchema()}://${getCurrentHost()}/timeselector"),
            LinkExample("Run barcode product scanner", "${getCurrentSchema()}://${getCurrentHost()}/barcode/product"),
            LinkExample("Run barcode card scanner", "${getCurrentSchema()}://${getCurrentHost()}/barcode/card"),
            LinkExample("create new GEAC card", "${getCurrentSchema()}://${getCurrentHost()}/account/card/signup"),
            LinkExample("link GEAC card", "${getCurrentSchema()}://${getCurrentHost()}/account/card/add"),
            LinkExample("ads GEAC card", "${getCurrentSchema()}://${getCurrentHost()}/account/card/advertisement"),
            LinkExample("Run barcode card scanner", "${getCurrentSchema()}://${getCurrentHost()}/barcode/card"),
            LinkExample("Show OSS licenses", "${getCurrentSchema()}://${getCurrentHost()}/licenses/all"),
            LinkExample("Braze Inbox", "${getCurrentSchema()}://${getCurrentHost()}/inbox"),
            LinkExample("Store not in store chooser", "${getCurrentSchema()}://${getCurrentHost()}/home/store?slug=tionesta")

        )
    }

    private fun getCurrentSchema(): String {
        return if (prodswitch.isChecked) SCHEMA_PROD
        else SCHEMA_STAGING
    }

    fun getCurrentHost() : String {
        return if (prodswitch.isChecked) HOST_PROD else HOST_STAGING
    }

    override fun onExampleClick(example: LinkExample) {
        url_edit_text.setText(example.url)
        url_edit_text.setSelection(url_edit_text.length())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val resCode = when (resultCode) {
            Activity.RESULT_OK -> "RESULT_OK"
            Activity.RESULT_CANCELED -> "RESULT_CANCELED"
            else -> "$resultCode"
        }
        result_text.text = "requestCode: $requestCode resultCode: $resCode\n ${data?.data?.path}"
    }

}
