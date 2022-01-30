package com.app.grofiesta.ui.main.view.address

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.apiClient.ApiClient
import com.app.grofiesta.data.apiClient.ApiInterface
import com.app.grofiesta.data.apiClient.NetworkHandling
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.RetryDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MyAddressRepository {

    private var mRepository: MyAddressRepository? = null

    fun getInstance(): MyAddressRepository {
        if (mRepository == null) {
            mRepository = MyAddressRepository()
        }
        return mRepository as MyAddressRepository
    }

    private var apiInterface: ApiInterface? = null

    constructor() {
        apiInterface = ApiClient.createService(ApiInterface::class.java)
    }


    @SuppressLint("CheckResult")
    fun callMyAddressListing(
        context: Context, productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyAddressList> {
        val mLiveData = MutableLiveData<ApiResponseModels.MyAddressList>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callMyAddressListing(productId)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnError {
                    (context as BaseActivity).dismissDialog()
                    NetworkHandling.showNetworkError(context, it)
                }.subscribe({
                    try {
                        (context as BaseActivity).dismissDialog()
                        mLiveData.value = it
                    } catch (e: Exception) {
                        println(e.printStackTrace())
                    }
                }, { error ->
                })
        } else {
            NetworkHandling.getRetryDialog(context, RetryDialog.NO_INTERNET)
        }
        return mLiveData
    }


    @SuppressLint("CheckResult")
    fun callDeleteAddress(
        context: Context, productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.ShipingChargeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callDeleteAddress(productId)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnError {
                    (context as BaseActivity).dismissDialog()
                    NetworkHandling.showNetworkError(context, it)
                }.subscribe({
                    try {
                        (context as BaseActivity).dismissDialog()
                        mLiveData.value = it
                    } catch (e: Exception) {
                        println(e.printStackTrace())
                    }
                }, { error ->
                })
        } else {
            NetworkHandling.getRetryDialog(context, RetryDialog.NO_INTERNET)
        }
        return mLiveData
    }

    @SuppressLint("CheckResult")
    fun callUpdateAddress(
        context: Context, postcode: String,address: String,hidden_address_id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.ShipingChargeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callUpdateAddress(postcode,address,hidden_address_id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnError {
                    (context as BaseActivity).dismissDialog()
                    NetworkHandling.showNetworkError(context, it)
                }.subscribe({
                    try {
                        (context as BaseActivity).dismissDialog()
                        mLiveData.value = it
                    } catch (e: Exception) {
                        println(e.printStackTrace())
                    }
                }, { error ->
                })
        } else {
            NetworkHandling.getRetryDialog(context, RetryDialog.NO_INTERNET)
        }
        return mLiveData
    }

    @SuppressLint("CheckResult")
    fun callAddAddress(
        context: Context, postcode: String,address: String,customer_id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.ShipingChargeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callAddAddress(postcode,address,customer_id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnError {
                    (context as BaseActivity).dismissDialog()
                    NetworkHandling.showNetworkError(context, it)
                }.subscribe({
                    try {
                        (context as BaseActivity).dismissDialog()
                        mLiveData.value = it
                    } catch (e: Exception) {
                        println(e.printStackTrace())
                    }
                }, { error ->
                })
        } else {
            NetworkHandling.getRetryDialog(context, RetryDialog.NO_INTERNET)
        }
        return mLiveData
    }


}