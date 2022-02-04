package com.app.grofiesta.ui.main.view.deliveryBoy

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


class DeliveryBoyRepository {

    private var mRepository: DeliveryBoyRepository? = null

    fun getInstance(): DeliveryBoyRepository {
        if (mRepository == null) {
            mRepository = DeliveryBoyRepository()
        }
        return mRepository as DeliveryBoyRepository
    }

    private var apiInterface: ApiInterface? = null

    constructor() {
        apiInterface = ApiClient.createService(ApiInterface::class.java)
    }


    @SuppressLint("CheckResult")
    fun callMyDeliveryListing(
        context: Context, productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyDeliveryResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.MyDeliveryResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callMyDeliveryListing(productId)
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
    fun callChageStatus(
        context: Context, order_id:String,username:String,useremail:String,order_status:String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyDeliveryResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.MyDeliveryResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callChageStatus(order_id,username,useremail,order_status)
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