package com.app.grofiesta.ui.main.view.product

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.ananda.retailer.Room.Repo.GroceryDBRepository
import com.ananda.retailer.Room.Tables.MyCart
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.room.response.MyCartResponse
import com.app.grofiesta.ui.main.view.login.OTPActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mContext: Context
    private var dbRepo: GroceryDBRepository? = null

    private var mRegistrationtApiRepository: ProductRepository? = null

    private fun initRepo() {
        if (dbRepo == null) dbRepo = GroceryDBRepository().getInstance()
    }


    init {
        initRepo()
    }

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

    fun initAddWishList(customer_id:String,
        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.AddWishListResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callAddWishList(mContext, customer_id,productId, showDialog)
            .let { return it }
    }
    fun initRemoveWishList(
                        productId: String, showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CommonRespose>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callRemoveWishList(mContext, productId, showDialog)
            .let { return it }
    }

    fun initListWishList(customer_id:String,
                            showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ProductListingResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callListWishList(mContext, customer_id, showDialog)
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

    fun initMyCartListing(
        customer_id: String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.ProductListingResponse>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callMyCartList(mContext,customer_id,  showDialog)
            .let { return it }
    }

    fun initDeleteMyCart(
        product_id: String,
        user_id: String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CommonRespose>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callDeleteMyCart(mContext,product_id,user_id,  showDialog)
            .let { return it }
    }

    fun initUpdateMyCart(
        product_id: String,
        user_id: String,
        qty:String,
        showDialog: Boolean
    ): MutableLiveData<ApiResponseModels.CommonRespose>? {
        mRegistrationtApiRepository = ProductRepository().getInstance()
        mRegistrationtApiRepository!!.callUpdateMyCart(mContext,product_id,user_id,  qty,showDialog)
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