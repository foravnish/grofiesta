package com.app.grofiesta.data.model

import java.io.Serializable

object ApiResponseModels {

    data class ImageDoc(var imgUrl: String, var type: String)


    data class GroupItemsResponse(
        val response: Response
    ) : Serializable {
        data class Response(
            val objItemGroup: ArrayList<ObjItemGroup>,
        ) : Serializable {
            data class ObjItemGroup(
                val id: String,
                val name: String,
                val objItemLst: ArrayList<ObjItemLst>
            ) : Serializable {
                data class ObjItemLst(
                    val id: String,
                    val name: String
                ) : Serializable
            }
        }
    }

    data class AddToCart(
        val status: ArrayList<Data>,
    ) : Serializable {
        data class Data(
            var product_id: String,
            var qty: String
        )
    }


    data class SuccessResponse(
        val success: Int
    )

    data class CommonRespose(
        val status: Boolean
    )

    data class AddWishListResponse(
        val status: Boolean,
        val last_wishlist_id:String
    )

    data class SignupResponse(
        val status: Boolean,
        val userid: String
    )

    data class MarqueeResponse(
        val status: Boolean,
        val data: String
    )

    data class ContactUsResponse(
        val status: Boolean,
        val data: ArrayList<Data>
    ) : Serializable {
        data class Data(
            var mobile: String,
            var email: String,
            var address: String,
            var facebook: String,
            var twitter: String,
            var instagram: String,
            var pintrest: String,
            var google: String,
        )
    }

    data class ShipingChargeResponse(
        val status: Boolean,
        val shipping: String,
        val distance: String
    )

    data class PlaceOrderResponse(
        val status: Boolean,
        val order_id: String
    )

    data class CouponCodeResponse(
        val status: Boolean,
        val data: Data
    ) : Serializable {
        data class Data(
            var coupon_val: String,
            var userid: String,

            )
    }

    data class LoginResponse(
        val status: Boolean,
        val data: Data
    ) : Serializable {
        data class Data(
            var customer_id: String,
            var firstname: String,
            var lastname: String,
            var email: String,
            var telephone: String,

            )
    }

    data class UserDetailResponse(
        val success: Success
    ) : Serializable {
        data class Success(
            var customer_id: String,
            val firstname: String,
            val lastname: String,
            val email: String,
            val telephone: String,
            val address: String,
            var address_id: String,
            var image: String,
            var urlimage: String,
            var delevery_boy_status: String,
            val country: String,
            val postcode: String,
            val city: String,
            val status: String,
        )
    }

    data class ProductListingResponse(
        var data: List<Data>,
        var status: Boolean
    ) {
        data class Data(
            var cart_id:String,
            var category_id: String,
            var description: String,
            var discount_percent: String,
            var display_price: String,
            var featured: String,
            var gfid: String,
            var qwantity:String,
            var gst: String,
            var hsn: String,
            var image: String,
            var keyword: String,
            var main_price: String,
            var product_id: String,
            var product_name: String,
            var purchase_price: String,
            var qty: String,
            var short_desp: String,
            var sku: String,
            var slug: String,
            var status: String,
            var sub_category_id: String,
            var urlimage: String,
            var weight_size: String,
            var hasWishList: Boolean = false,
            var wi_id:String,
            var minimum_price:String,
        )
    }


    data class RelatedProductResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            var product_id: String,
            var gfid: String,
            var sub_category_id: String,
            var product_name: String,
            var weight_size: String,
            var main_price: String,
            var display_price: String,
            var purchase_price: String,
            var discount_percent: String,
            var description: String,
            var short_desp: String,
            var urlimage: String,
            var qty: String,
            var keyword: String,
            var sku: String,
            var hsn: String,
            var gst: String,
            var slug: String,
            var featured: String,
            var Stringstatus: String,
            var category_name: String,
            var cart_id:String
        )
    }

    data class OffersResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            var oid: String,
            var name: String,
            var urlimage: String,
            var offer_status: String
        )
    }

    data class MyWallet(
        val data: Data
    ) : Serializable {
        data class Data(
            var debit: String,
            var credit: String,
            var current_balance: CurrentBalance,
            var history: ArrayList<History>

        ) : Serializable {
            data class CurrentBalance(
                var wid: String,
                var customer_id: String,
                var wallet_value: String
            )

            data class History(
                var wh_id: String,
                var wid: String,
                var debit: String,
                var credit: String

            )


        }

    }

    data class MyAddressList(
        var status: Boolean,
        val data: ArrayList<DataAddress>
    ) : Serializable {
        data class DataAddress(
            var address_id: String,
            var cid: String,
            var postcode: String,
            var address: String,
        ) : Serializable
    }

//    data class MyOderResponse(
//        var status: Boolean,
//        val data: ArrayList<Success>
//    ) : Serializable {
//        data class Success(
//            var od_id: String,
//            var order_id: String,
//            var product_id: String,
//            var item_name: String,
//            var quantity: String,
//            var price: String,
//            var customer_id: String,
//            var status: String,
//            var date_added: String,
//        )
//    }

    data class OrderLIstingNewResponse(
        var `data`: List<Data>,
        var status: Boolean
    ) {
        data class Data(
            var address: String,
            var coupon_val: String,
            var customer_email: String,
            var customer_id: String,
            var customer_mobile: String,
            var customer_name: String,
            var date_added: String,
            var order_detail_count: Int,
            var order_detail_data: List<OrderDetailData>,
            var order_id: String,
            var payment_method: String,
            var postcode: String,
            var shipping_charge: String,
            var status: String,
            var total: String
        ) {
            data class OrderDetailData(
                var customer_id: String,
                var date_added: String,
                var delevery_boy_id: String,
                var item_name: String,
                var order_detail_id: String,
                var order_id: String,
                var price: String,
                var product_id: String,
                var quantity: String
            )
        }
    }

    data class ProductDetailResponse(
        val success: Success
    ) : Serializable {
        data class Success(
            var product_id: String,
            var gfid: String,
            var category_id: String,
            var sub_category_id: String,
            var product_name: String,
            var weight_size: String,
            var main_price: String,
            var display_price: String,
            var purchase_price: String,
            var discount_percent: String,
            var description: String,
            var short_desp: String,
            var urlimage: String,
            var qty: String,
            var keyword: String,
            var sku: String,
            var hsn: String,
            var gst: String,
            var slug: String,
            var featured: String,
            var Stringstatus: String,
            var category_name: String,
            var minimum_price:String,
        )
    }


    data class SendOtpResponse(
        var data: Data,
    ) {
        data class Data(
            var email: String,
            var opt: Int
        )
    }

    data class BannerResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            val slider_id: String = "",
            val urlimage: String = "",
        )
    }

    data class DymainHomeProductResponse(
        var data: List<Data>,
        var status: Boolean
    ) {
        data class Data(
            var category_name: String,
            var images: String,
            var module_status: String,
            var module_banner: List<String>,
            var productsdata: List<Productsdata>
        ) {
            data class Productsdata(
                var display_price: String,
                var discount_percent: String,
                var image: String,
                var main_price: String,
                var product_id: String,
                var product_name: String,
                var weight_size: String,
                var hasCart: Boolean = false
            )
        }
    }

    data class GroProductResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            val product_id: String = "",
            val product_name: String = "",
            val weight_size: String = "",
            val main_price: String = "",
            val discount_percent: String = "",
            val display_price: String = "",
            val urlimage: String = "",
            var hasCart: Boolean = false
        )
    }

    data class BestSellerResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            val category_id: String = "",
            val gfid: String = "",
            val category_name: String = "",
            val category_image: String = "",
            val bestseller: String = "",
            val desp: String = "",
            val urlimage: String = "",

            )
    }

    data class Services(
        val success: Success
    ) : Serializable {
        data class Success(
            val id: String = "",
            val description: String = "",
        ) : Serializable {

        }
    }

    data class DropDownResponse(
        val success: ArrayList<Success>
    ) : Serializable {
        data class Success(
            val category_id: String = "",
            val gfid: String = "",
            val category_name: String = "",
            val category_image: String = "",
            val children: ArrayList<Children>
        ) : Serializable {
            data class Children(
                val sid: String = "",
                val name: String = "",
            )

        }
    }

    data class GroFiestaPageResponse(
        var success: Success
    ) {
        data class Success(
            var image: List<Image>,
            var slider: List<Slider>,
            var text: Text,
            var breadcrumb_banner: String
        ) {
            data class Image(
                var date_added: String,
                var urlimage: String,
                var id: String,
                var image: String,
                var name: String,
                var offer_status: String
            )

            data class Slider(
                var banner_id: String,
                var category_page: String,
                var company_name: String,
                var date_added: String,
                var date_from: String,
                var date_to: String,
                var email: String,
                var urlimage: String,
                var home_page: String,
                var image: String,
                var link: String,
                var mobile: String,
                var status: String
            )

            data class Text(
                var fiesta: String,
                var fiestatitle: String,
                var gro: String,
                var grotitle: String,
                var id: String
            )
        }
    }

    data class TncPrivacyAboutUsResponse(
        var about: About,
        var privecy_policy: PrivecyPolicy,
        var tnc: Tnc
    ) {
        data class About(
            var award: String,
            var client: String,
            var desc1: String,
            var desc2: String,
            var fresh: String,
            var grofiesta: String,
            var id: String,
            var image: String,
            var marquee: String,
            var meta_description: String,
            var meta_keywords: String,
            var meta_tags: String,
            var payment: String,
            var policy: String,
            var products: String,
            var produse: String,
            var sub_title: String,
            var supprot: String,
            var team: String,
            var text1: String,
            var text2: String,
            var text3: String,
            var text4: String,
            var title1: String,
            var title2: String,
            var urlimage: String
        )

        data class PrivecyPolicy(
            var description: String,
            var id: String,
            var meta_description: String,
            var meta_keywords: String,
            var meta_tags: String,
            var urlimage: String
        )

        data class Tnc(
            var description: String,
            var id: String,
            var meta_description: String,
            var meta_keywords: String,
            var meta_tags: String,
            var urlimage: String
        )
    }


    data class MyDeliveryResponse(
        var `data`: List<Data>,
        var status: Boolean
    ) {
        data class Data(
            var address: String,
            var customer_email: String,
            var customer_id: String,
            var customer_mobile: String,
            var customer_name: String,
            var status:String,
            var order_detail: List<OrderDetail>,
            var order_id: String
        ) {
            data class OrderDetail(
                var customer_id: String,
                var date_added: String,
                var delevery_boy_id: String,
                var item_name: String,
                var order_detail_id: String,
                var order_id: String,
                var price: String,
                var product_id: String,
                var quantity: String
            )
        }
    }


}