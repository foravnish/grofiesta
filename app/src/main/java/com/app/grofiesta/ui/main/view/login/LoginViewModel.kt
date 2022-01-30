package com.app.grofiesta.ui.main.view.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.data.model.request.*
import com.app.grofiesta.ui.main.view.home.HomeRepository


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: LoginRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initSendOtp(
        req: SendOtpRequest, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SendOtpResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.callSendOtp(mContext, req, showDialog)
            .let { return it }
    }

    fun verifyOtpApi(
        post_otp: String,locaOtp:String,email:String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.LoginResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.callVerifyOtpApi(mContext,  post_otp,locaOtp,email, showDialog)
            .let { return it }
    }

    fun getLogin(
        mobNum: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.LoginResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.getLogin(mContext, mobNum, showDialog)
            .let { return it }
    }


    fun getRegistration(
        firstname: String,
        lastname: String,
        email: String,
        telephone: String,
        address: String,
        postcode: String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SignupResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.getRegistration(mContext,
            firstname,
            lastname,
            email,
            telephone,
            address,
            postcode,
            showDialog)
            .let { return it }
    }

    fun callUserDetail(
        id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.UserDetailResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.callUserDetail(mContext, id, showDialog)
            .let { return it }
    }

    fun callSaveUserDetail(
        req: UserProfileRequest, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SuccessResponse>? {
        mRegistrationtApiRepository = LoginRepository().getInstance()
        mRegistrationtApiRepository!!.callSaveUserDetail(mContext, req, showDialog)
            .let { return it }
    }




}