package com.app.grofiesta.ui.main.view.order

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class MyOrderViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: MyOrderRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initMyOderList(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyOderResponse>? {
        mRegistrationtApiRepository = MyOrderRepository().getInstance()
        mRegistrationtApiRepository!!.callMyOrderListing(mContext, productId, showDialog)
            .let { return it }
    }



}