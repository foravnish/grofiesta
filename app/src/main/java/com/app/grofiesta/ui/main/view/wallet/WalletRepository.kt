package com.app.grofiesta.ui.main.view.wallet

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


class WalletRepository {

    private var mRepository: WalletRepository? = null

    fun getInstance(): WalletRepository {
        if (mRepository == null) {
            mRepository = WalletRepository()
        }
        return mRepository as WalletRepository
    }

    private var apiInterface: ApiInterface? = null

    constructor() {
        apiInterface = ApiClient.createService(ApiInterface::class.java)
    }


    @SuppressLint("CheckResult")
    fun callMyWallet(
        context: Context,id:String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyWallet> {
        val mLiveData = MutableLiveData<ApiResponseModels.MyWallet>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callMyWallet(id)
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