package com.app.grofiesta.ui.main.view.wallet

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class WalletModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: WalletRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initMyWallet(id:String,
         showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MyWallet>? {
        mRegistrationtApiRepository = WalletRepository().getInstance()
        mRegistrationtApiRepository!!.callMyWallet(mContext, id,showDialog)
            .let { return it }
    }



}