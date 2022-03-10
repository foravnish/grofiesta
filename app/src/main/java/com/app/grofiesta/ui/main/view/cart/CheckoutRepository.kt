package com.app.grofiesta.ui.main.view.cart

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
import retrofit2.http.Field


class CheckoutRepository {

    private var mRepository: CheckoutRepository? = null

    fun getInstance(): CheckoutRepository {
        if (mRepository == null) {
            mRepository = CheckoutRepository()
        }
        return mRepository as CheckoutRepository
    }

    private var apiInterface: ApiInterface? = null

    constructor() {
        apiInterface = ApiClient.createService(ApiInterface::class.java)
    }


    @SuppressLint("CheckResult")
    fun callCoupon(
        context: Context, edtCoupon: String, userId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CouponCodeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.CouponCodeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callCoupon(edtCoupon, userId)
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
    fun callShippingCharge(
        context: Context, postcode: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.ShipingChargeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callShippingCharge(postcode)
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
    fun getCouponsList(
        context: Context,  showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CouponListResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.CouponListResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.getCouponsList()
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
    fun callPlaceOrder(
        context: Context,
        customer_id: String,
        customer_name: String,
        customer_email: String,
        customer_mobile: String,
        wallet_value: String,
        payment_method: String,
        status: String,
        shipping_charge: String,
        gst_val: String,
        total: String,
        coupon_val: String,
        postcode:String,
        address:String,
        debit_amount:String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.PlaceOrderResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.PlaceOrderResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callPlaceOrder(
                customer_id,
                customer_name,
                customer_email,
                customer_mobile,
                wallet_value,
                payment_method,
                status,
                shipping_charge,
                gst_val,
                total,
                coupon_val,
                postcode,
                address,
                debit_amount
            )
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
    fun callAddToCart(
        context: Context,
        customer_id: String,
        product_id: String,
        quantity: String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse> {
        val mLiveData = MutableLiveData<ApiResponseModels.ShipingChargeResponse>()
        if (NetworkHandling.isConnected(context)) {
            if (showDialog) (context as BaseActivity).showDialog()
            apiInterface!!.callAddToCart(customer_id, product_id, quantity)
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