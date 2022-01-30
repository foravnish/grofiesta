package com.app.grofiesta.ui.main.view.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context


    private var mRegistrationtApiRepository: HomeRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun iniBannerAPi(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.BannerResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callBannerAPi(mContext, showDialog)
            .let { return it }
    }


    fun initGroProducts(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.GroProductResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callGroProducts(mContext, showDialog)
            .let { return it }
    }

    fun initFiestaProducts(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.GroProductResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callFiestaProducts(mContext, showDialog)
            .let { return it }
    }

    fun initBestSellerProducts(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.BestSellerResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callBestSellerProducts(mContext, showDialog)
            .let { return it }
    }

    fun initDymanicProducts(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.DymainHomeProductResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callDymanicProducts(mContext, showDialog)
            .let { return it }
    }

    fun initMarquee(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.MarqueeResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callMarquee(mContext, showDialog)
            .let { return it }
    }

    fun initContactUs(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ContactUsResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callContactUs(mContext, showDialog)
            .let { return it }
    }

    fun initAboutTnCPrivacy(showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.TncPrivacyAboutUsResponse>? {
        mRegistrationtApiRepository = HomeRepository().getInstance()
        mRegistrationtApiRepository!!.callAboutTnCPrivacy(mContext, showDialog)
            .let { return it }
    }


}