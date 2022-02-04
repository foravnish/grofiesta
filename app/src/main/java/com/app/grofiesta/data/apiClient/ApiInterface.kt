package com.app.grofiesta.data.apiClient

import com.app.grofiesta.data.api.ApiUrls
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.data.model.request.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

import com.google.gson.JsonObject
import retrofit2.Call

import retrofit2.http.POST

import retrofit2.http.Multipart


interface ApiInterface {

    @FormUrlEncoded
    @POST(ApiUrls.CHECK_FOR_EXIST)
    fun getLogin(
        @Field("mobNum") mobNum: String
    ): Observable<ApiResponseModels.LoginResponse>

    @POST(ApiUrls.SEND_OTP)
    fun callSendOtp(
        @Body body: SendOtpRequest
    ): Observable<ApiResponseModels.SendOtpResponse>

    @FormUrlEncoded
    @POST(ApiUrls.VERIFY_OTP)
    fun callVerifyOtpApi(
        @Field("post_otp") post_otp: String,
        @Field("local_otp") local_otp: String,
        @Field("email") email: String
    ): Observable<ApiResponseModels.LoginResponse>

    @FormUrlEncoded
    @POST(ApiUrls.REGISTRATION)
    fun getRegistration(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String,
        @Field("telephone") telephone: String,
        @Field("address") address: String,
        @Field("postcode") postcode: String
    ): Observable<ApiResponseModels.SignupResponse>

    @GET(ApiUrls.BANNER_DATA)
    fun callBannerAPi(
    ): Observable<ApiResponseModels.BannerResponse>

    @GET(ApiUrls.GRO_PRODUCTS)
    fun getcallGroProducts(
    ): Observable<ApiResponseModels.GroProductResponse>

    @GET(ApiUrls.FIESTA_PRODUCTS)
    fun callFiestaProducts(
    ): Observable<ApiResponseModels.GroProductResponse>

    @GET(ApiUrls.DYNAMIC_PRODUCTS)
    fun callDymanicProducts(
    ): Observable<ApiResponseModels.DymainHomeProductResponse>

    @GET(ApiUrls.MARQUEE)
    fun callMarquee(
    ): Observable<ApiResponseModels.MarqueeResponse>

    @GET(ApiUrls.CONTACT_US)
    fun callContactUs(
    ): Observable<ApiResponseModels.ContactUsResponse>


    @GET(ApiUrls.TNC_PRIVACY_ABOUT_US)
    fun callAboutTnCPrivacy(
    ): Observable<ApiResponseModels.TncPrivacyAboutUsResponse>

    @GET(ApiUrls.BEST_SELLER_PRODUCTS)
    fun callBestSellerProducts(
    ): Observable<ApiResponseModels.BestSellerResponse>

    @GET(ApiUrls.PRODUCT_DETAIL + "/{id}")
    fun callProductDetail(
        @Path("id") id: String
    ): Observable<ApiResponseModels.ProductDetailResponse>

    @FormUrlEncoded
    @POST(ApiUrls.ADD_WISHLIST)
    fun callAddWishList(
        @Field("customer_id") customer_id: String,
        @Field("product_id") product_id: String
    ): Observable<ApiResponseModels.AddWishListResponse>

    @GET(ApiUrls.REMOVE_WISHLIST+ "/{id}")
    fun callRemoveWishList(
        @Path("id") id: String,
    ): Observable<ApiResponseModels.CommonRespose>

    @FormUrlEncoded
    @POST(ApiUrls.LISTING_WISHLIST)
    fun callListWishList(
        @Field("customer_id") customer_id: String,
    ): Observable<ApiResponseModels.ProductListingResponse>

    @FormUrlEncoded
    @POST(ApiUrls.SEARCH_PRODUCT)
    fun callSearchData(
        @Field("keyword") keyword: String
    ): Observable<ApiResponseModels.ProductListingResponse>

    @GET(ApiUrls.GRO_PAGE)
    fun callGroPage(): Observable<ApiResponseModels.GroFiestaPageResponse>

    @GET(ApiUrls.FIESTA_PAGE)
    fun callFiestaPage(): Observable<ApiResponseModels.GroFiestaPageResponse>

    @GET(ApiUrls.RELEATED_PRODUCT + "/{id}")
    fun callRelatedProducts(
        @Path("id") id: String
    ): Observable<ApiResponseModels.RelatedProductResponse>

    @GET(ApiUrls.DROP_DOWN_GRO)
    fun callDropDownGroData(
    ): Observable<ApiResponseModels.DropDownResponse>


    @GET(ApiUrls.MY_SERVIES)
    fun callMyServices(
    ): Observable<ApiResponseModels.Services>

    @FormUrlEncoded
    @POST(ApiUrls.MY_CART_LIST)
    fun callMyCartList(
        @Field("customer_id") customer_id: String
    ): Observable<ApiResponseModels.ProductListingResponse>

    @FormUrlEncoded
    @POST(ApiUrls.MY_CART_DELETE)
    fun callDeleteMyCart(
        @Field("cart_id") cart_id: String
    ): Observable<ApiResponseModels.CommonRespose>

    @FormUrlEncoded
    @POST(ApiUrls.MY_CART_UPDATE)
    fun callUpdateMyCart(
        @Field("cart_id") cart_id: String,
        @Field("qty") qty: String,
        ): Observable<ApiResponseModels.CommonRespose>

    @GET(ApiUrls.DROP_DOWN_FIESTA)
    fun callDropDownFiestaData(
    ): Observable<ApiResponseModels.DropDownResponse>

    @GET(ApiUrls.DROP_DOWN_SERVICE)
    fun callServiceFiestaData(
    ): Observable<ApiResponseModels.DropDownResponse>

    @FormUrlEncoded
    @POST(ApiUrls.MY_ORDER_LISTING)
    fun callMyOrderListing(
        @Field("customer_id") customer_id: String
    ): Observable<ApiResponseModels.OrderLIstingNewResponse>

    @GET(ApiUrls.MY_DELIVERY_LISTING + "/{id}")
    fun callMyDeliveryListing(
        @Path("id") id: String
    ): Observable<ApiResponseModels.MyDeliveryResponse>

    @FormUrlEncoded
    @POST(ApiUrls.MY_DELIVERY_CHANGE_STATUS )
    fun callChageStatus(
        @Field("order_id") order_id: String,
        @Field("username") username: String,
        @Field("useremail") useremail: String,
        @Field("order_status") order_status: String,

    ): Observable<ApiResponseModels.MyDeliveryResponse>

    @GET(ApiUrls.MY_DROPDOWN_LISTING + "/{id}")
    fun callDropDownList(
        @Path("id") id: String
    ): Observable<ApiResponseModels.ProductListingResponse>

    @GET(ApiUrls.OFFER_LISTING)
    fun callOfferListing(
    ): Observable<ApiResponseModels.OffersResponse>

    @GET(ApiUrls.MY_WALLET + "/{id}")
    fun callMyWallet(
        @Path("id") id: String
    ): Observable<ApiResponseModels.MyWallet>

    @GET(ApiUrls.MY_PROFILE + "/{id}")
    fun callUserDetail(
        @Path("id") id: String
    ): Observable<ApiResponseModels.UserDetailResponse>

    @FormUrlEncoded
    @POST(ApiUrls.COUPAN)
    fun callCoupon(
        @Field("couponval") couponval: String, @Field("customer_id") customer_id: String
    ): Observable<ApiResponseModels.CouponCodeResponse>

    @FormUrlEncoded
    @POST(ApiUrls.SHIPPING_CHARGE)
    fun callShippingCharge(
        @Field("postcode") postcode: String
    ): Observable<ApiResponseModels.ShipingChargeResponse>

    @FormUrlEncoded
    @POST(ApiUrls.PLACE_ORDER)
    fun callPlaceOrder(
        @Field("customer_id") customer_id: String,
        @Field("customer_name") customer_name: String,
        @Field("customer_email") customer_email: String,
        @Field("customer_mobile") customer_mobile: String,
        @Field("wallet_value") wallet_value: String,
        @Field("payment_method") payment_method: String,
        @Field("status") status: String,
        @Field("shipping_charge") shipping_charge: String,
        @Field("gst_val") gst_val: String,
        @Field("total") total: String,
        @Field("coupon_val") coupon_val: String,
        @Field("postcode") postcode: String,
        @Field("address") address: String,

        ): Observable<ApiResponseModels.PlaceOrderResponse>

    @FormUrlEncoded
    @POST(ApiUrls.ADD_TO_CART)
    fun callAddToCart(
        @Field("customer_id") customer_id: String,
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String
    ): Observable<ApiResponseModels.ShipingChargeResponse>

    @FormUrlEncoded
    @POST(ApiUrls.MY_ADDRESS)
    fun callMyAddressListing(
        @Field("customer_id") customer_id: String
    ): Observable<ApiResponseModels.MyAddressList>

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ADDRESS)
    fun callDeleteAddress(
        @Field("address_id") customer_id: String
    ): Observable<ApiResponseModels.ShipingChargeResponse>

    @FormUrlEncoded
    @POST(ApiUrls.UPDATE_ADDRESS)
    fun callUpdateAddress(
        @Field("postcode") postcode: String,
        @Field("address") address: String, @Field("hidden_address_id") hidden_address_id: String
    ): Observable<ApiResponseModels.ShipingChargeResponse>

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ADDRESS)
    fun callAddAddress(
        @Field("postcode") postcode: String,
        @Field("address") address: String, @Field("customer_id") customer_id: String
    ): Observable<ApiResponseModels.ShipingChargeResponse>


    @Multipart
    @POST(ApiUrls.SAVE_PROFILE)
    fun callSaveUserDetail(
        @Part("name") firstname: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("hidden_customer_id") hidden_customer_id: RequestBody,
        @Part("address") address: RequestBody,
        @Part("imageedit") imageedit: RequestBody,
        @Part multipartTypedOutput: MultipartBody.Part?
    ): Observable<ApiResponseModels.SuccessResponse>

    @Multipart
    @POST(ApiUrls.ADD_SERVICE)
    fun callAddService(
        @Part("customer_id") customer_id: RequestBody,
        @Part("description") description: RequestBody,
        @Part multipartTypedOutput: List<MultipartBody.Part?>
    ): Observable<ApiResponseModels.ShipingChargeResponse>

    @Multipart
    @POST(ApiUrls.SEND_FEEDBACK)
    fun callSendFeedback(
        @Part("customer_id") customer_id: RequestBody,
        @Part("remark") remark: RequestBody,
        @Part multipartTypedOutput: List<MultipartBody.Part?>
    ): Observable<ApiResponseModels.ShipingChargeResponse>

}