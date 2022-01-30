package com.app.grofiesta.ui.main.view.cart

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.data.model.request.*
import com.app.grofiesta.ui.main.view.home.HomeRepository


class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: CheckoutRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initCoupon(
        edtCoupon: String, userId:String,showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CouponCodeResponse>? {
        mRegistrationtApiRepository = CheckoutRepository().getInstance()
        mRegistrationtApiRepository!!.callCoupon(mContext, edtCoupon, userId, showDialog)
            .let { return it }
    }

    fun initShippingCharge(
        postcode: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = CheckoutRepository().getInstance()
        mRegistrationtApiRepository!!.callShippingCharge(mContext, postcode, showDialog)
            .let { return it }
    }


    fun initPlaceOrder(
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
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.PlaceOrderResponse>? {
        mRegistrationtApiRepository = CheckoutRepository().getInstance()
        mRegistrationtApiRepository!!.callPlaceOrder(mContext,
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
            coupon_val,postcode,address, showDialog)
            .let { return it }
    }

    fun initAddToCart(
        customer_id: String,product_id: String,quantity: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = CheckoutRepository().getInstance()
        mRegistrationtApiRepository!!.callAddToCart(mContext, customer_id,product_id,quantity, showDialog)
            .let { return it }
    }




}