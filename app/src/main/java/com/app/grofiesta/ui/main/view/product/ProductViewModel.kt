package com.app.grofiesta.ui.main.view.product

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grofiesta.data.model.ApiResponseModels
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context

    private var mRegistrationtApiRepository: ProductRepository? = null

    fun init(context: Context) {
        this.mContext = context
    }

    fun initProductDetail(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ProductDetailResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callProductDetail(mContext, productId, showDialog)
            .let { return it }
    }

    fun initSearchData(
        keyword: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ProductListingResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callSearchData(mContext, keyword, showDialog)
            .let { return it }
    }

    fun initRelatedProduct(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.RelatedProductResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callRelatedProducts(mContext, productId, showDialog)
            .let { return it }
    }


    fun initDropDownGroData(
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.DropDownResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callDropDownGroData(mContext,  showDialog)
            .let { return it }
    }

    fun initMyServices(
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.Services>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callMyServices(mContext,  showDialog)
            .let { return it }
    }


    fun initDropDownFiestaData(
         showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.DropDownResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callDropDownFiestaData(mContext, showDialog)
            .let { return it }
    }

    fun initServiceFiestaData(
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.DropDownResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callServiceFiestaData(mContext, showDialog)
            .let { return it }
    }

    fun initGroPage(
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.GroFiestaPageResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callGroPage(mContext, showDialog)
            .let { return it }
    }

    fun initFiestaPage(
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.GroFiestaPageResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callFiestaPage(mContext, showDialog)
            .let { return it }
    }

    fun initDropDownList(
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ProductListingResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callDropDownList(mContext, productId, showDialog)
            .let { return it }
    }

    fun initAddSeries(
        customer_id: RequestBody, description: RequestBody,
        image: List<MultipartBody.Part?>, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callAddService(mContext, customer_id,description,image, showDialog)
            .let { return it }
    }

    fun initSendFeedback(
        customer_id: RequestBody, remark: RequestBody,
        image: List<MultipartBody.Part?>, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ShipingChargeResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callSendFeedback(mContext, customer_id,remark,image, showDialog)
            .let { return it }
    }

}