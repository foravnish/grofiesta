package com.app.grofiesta.data.model.request

data class UserProfileRequest(
    var hidden_customer_id:String,
    var address: String,
    var country: String,
    var email: String,
    var firstname: String,
    var lastname: String,
    var postcode: String
)

data class CouponRequest(
    var couponval:String,
    var customer_id: String,
)

