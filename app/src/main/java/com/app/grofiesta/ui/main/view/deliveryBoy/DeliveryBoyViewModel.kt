package com.app.grofiesta.ui.main.view.deliveryBoy

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class DeliveryBoyViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: DeliveryBoyRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initMyDeliveryList(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyDeliveryResponse>? {
        mRegistrationtApiRepository = DeliveryBoyRepository().getInstance()
        mRegistrationtApiRepository!!.callMyDeliveryListing(mContext, productId, showDialog)
            .let { return it }
    }

    fun initChageStatus(
        order_id:String,username:String,useremail:String,order_status:String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyDeliveryResponse>? {
        mRegistrationtApiRepository = DeliveryBoyRepository().getInstance()
        mRegistrationtApiRepository!!.callChageStatus(mContext, order_id,username,useremail,order_status, showDialog)
            .let { return it }
    }



}