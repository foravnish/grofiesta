package com.app.grofiesta.ui.main.view.offers

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class OfferViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: OfferRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun iniOfferList(
         showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.OffersResponse>? {
        mRegistrationtApiRepository = OfferRepository().getInstance()
        mRegistrationtApiRepository!!.callOfferList(mContext, showDialog)
            .let { return it }
    }



}