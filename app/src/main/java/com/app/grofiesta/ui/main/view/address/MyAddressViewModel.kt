package com.app.grofiesta.ui.main.view.address

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class MyAddressViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: MyAddressRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initMyAddressList(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyAddressList>? {
        mRegistrationtApiRepository = MyAddressRepository().getInstance()
        mRegistrationtApiRepository!!.callMyAddressListing(mContext, productId, showDialog)
            .let { return it }
    }

    fun initDeleteAddress(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = MyAddressRepository().getInstance()
        mRegistrationtApiRepository!!.callDeleteAddress(mContext, productId, showDialog)
            .let { return it }
    }

    fun initUpdateAddress(
        postcode: String,address: String,hidden_address_id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = MyAddressRepository().getInstance()
        mRegistrationtApiRepository!!.callUpdateAddress(mContext, postcode,address,hidden_address_id, showDialog)
            .let { return it }
    }

    fun initAddNewAddress(
        postcode: String,address: String,customer_id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = MyAddressRepository().getInstance()
        mRegistrationtApiRepository!!.callAddAddress(mContext, postcode,address,customer_id, showDialog)
            .let { return it }
    }


}