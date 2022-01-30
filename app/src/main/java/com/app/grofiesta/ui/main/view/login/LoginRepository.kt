package com.app.grofiesta.ui.main.view.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.apiClient.ApiClient
import com.app.grofiesta.data.apiClient.ApiInterface
import com.app.grofiesta.data.apiClient.NetworkHandling
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.data.model.request.*
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.RetryDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginRepository {

    private var mRepository: LoginRepository? = null

    fun getInstance(): LoginRepository {
        if (mRepository == null) {
            mRepository = LoginRepository()
        }
        return mRepository as LoginRepository
    }

    private var apiInterface: ApiInterface? = null

    constructor() {
        apiInterface = ApiClient.createService(ApiInterface::class.java)
    }


    @SuppressLint("CheckResult")
    fun getLogin(
        context: Context, mobNum: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.LoginResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.LoginResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.getLogin(mobNum)
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
    fun callSendOtp(
        context: Context, req: SendOtpRequest, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SendOtpResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.SendOtpResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callSendOtp(req)
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
    fun callVerifyOtpApi(
        context: Context, post_otp: String,locaOtp:String,email:String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.LoginResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.LoginResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callVerifyOtpApi( post_otp,locaOtp,email)
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
    fun getRegistration(
        context: Context,
        firstname: String,
        lastname: String,
        email: String,
        telephone: String,
        address: String,
        postcode: String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SignupResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.SignupResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.getRegistration(
                firstname,
                lastname,
                email,
                telephone,
                address,
                postcode)
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
    fun callUserDetail(
        context: Context, id: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.UserDetailResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.UserDetailResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callUserDetail(id)
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
    fun callSaveUserDetail(
        context: Context, req:UserProfileRequest, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.SuccessResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.SuccessResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callSaveUserDetail(req)
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