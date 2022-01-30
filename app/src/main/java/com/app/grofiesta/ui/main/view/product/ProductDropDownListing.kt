package com.app.grofiesta.ui.main.view.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.grofiesta.R
import com.app.grofiesta.adapter.ExpandableAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.login.OTPActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_product_drop_down_listing.*
import kotlinx.android.synthetic.main.activity_signup.*

class ProductDropDownListing : BaseActivity() {
    var mType = ""
    var mSelectedPos = -1
    val mContext by lazy { this@ProductDropDownListing }

    lateinit var mViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_drop_down_listing)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@ProductDropDownListing)

        imgBack.setOnClickListener { finish() }


        mType = intent.getStringExtra("type")!!
        if (mType == "Gro") {
            txtPageTitle.text = "Shop by Gro"
            getCallGroDropDownApi()
        } else if (mType == "Fiesta") {
            txtPageTitle.text = "Shop by Fiesta"
            getCallFiestaDropDownApi()
        }

    }

    private fun getCallGroDropDownApi() {
        mViewModel.initDropDownGroData(true)!!.observe(this, Observer {
            if (it.success != null) {
                if (it.success.size > 0)
                    setCategoriesListNew(it.success)

            } else Utility.showToast(mContext)
        })

   }


    private fun getCallFiestaDropDownApi() {
        mViewModel.initDropDownFiestaData(true)!!.observe(this, Observer {
            if (it.success != null) {

                if (it.success.size > 0)
                    setCategoriesListNew(it.success)

            } else Utility.showToast(mContext)
        })

    }

    private fun getCallServicesDropDownApi() {
        mViewModel.initServiceFiestaData(true)!!.observe(this, Observer {
            if (it.success != null) {

                if (it.success.size > 0)
                    setCategoriesListNew(it.success)

            } else Utility.showToast(mContext)
        })

    }

    private fun setCategoriesListNew(
        mList: ArrayList<ApiResponseModels.DropDownResponse.Success>
    ) {
        rvItemCategories.visibility = View.VISIBLE
        txtEmptyView.visibility = View.GONE
        rvItemCategories.setHasFixedSize(true)
        rvItemCategories.layoutManager = LinearLayoutManager(this)
        val mAdapter = ExpandableAdapter(this, mList) {

            Intent(this, ProductListActivity::class.java).apply {
                putExtra("sid",""+it)
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }
        }
        rvItemCategories.adapter = mAdapter
        mAdapter.openSelectedPosition()
    }

}