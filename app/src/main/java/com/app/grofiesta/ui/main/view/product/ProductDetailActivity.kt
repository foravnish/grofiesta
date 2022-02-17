package com.app.grofiesta.ui.main.view.product

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.adapter.ReleatedProductAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityProductDetailBinding
import com.app.grofiesta.room.response.MyCartResponse
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.cart.MyCartActivity
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_main_header_layout_with_search_trans.*
import kotlinx.android.synthetic.main.home_product_item.view.*

import android.animation.Animator
import android.os.Handler
import android.widget.ImageView
import android.widget.LinearLayout

import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.BannerGroFiestaPagerAdapter
import com.app.grofiesta.ui.main.view.cart.CheckoutViewModel
import com.app.grofiesta.utils.animUtil.CircleAnimationUtil
import kotlinx.android.synthetic.main.dymanic_home_product_item.view.*
import kotlinx.android.synthetic.main.footer_detail.view.*


class ProductDetailActivity : BaseActivity() {
    lateinit var binding: ActivityProductDetailBinding
    var value = 1
    var flag: Boolean = true
    var product_id = ""
    var mWid = ""
    lateinit var mViewModel: ProductViewModel
    lateinit var mViewModelCheckout: CheckoutViewModel

    val viewModel: GroceryViewModel by viewModels()
    var flagCart: Boolean = false
    val mContext by lazy { this@ProductDetailActivity }
    lateinit var list: List<MyCart>
    lateinit var mMyCartData: ApiResponseModels.ProductListingResponse.Data
    lateinit var mData: ApiResponseModels.ProductDetailResponse.Success.ProductDetail
    var mImage = ""
    private var NUM_PAGES = 0
    internal val PERIOD_MS: Long = 3 * 1000
    private var handler = Handler()
    internal var position: Int = 0

//    public override fun onPause() {
//        super.onPause()
//        handler?.removeCallbacks(runnale)
//    }

    override fun onResume() {
        super.onResume()
        getAllMyCart()
        getMyCart(product_id)
//        handler.postDelayed(runnale, PERIOD_MS)
    }

//    private val runnale = object : Runnable {
//        override fun run() {
//            binding.viewpager.setCurrentItem(position, true)
//            if (position >= NUM_PAGES)
//                position = 0
//            else
//                position++
//            // Move to the next page after 3s
//            handler.postDelayed(this, PERIOD_MS)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@ProductDetailActivity)
        mViewModelCheckout = ViewModelProvider.AndroidViewModelFactory(application)
            .create(CheckoutViewModel::class.java)
        mViewModelCheckout.init(this)
        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Product Detail"
        product_id = intent.getStringExtra("product_id")!!

        binding.lytFooterDetail.alpha = 0.5f
        binding.lytFooterDetail.isEnabled = false

        callDetailAPi()

        callMyWishList(product_id)

//        binding.productImage.setOnClickListener {
//            Intent(this, ImagePreviewActivity::class.java).apply {
//                putExtra("image", mImage)
//            }.let {
//                Utility.startActivityWithLeftToRightAnimation(this, it)
//            }
//
//        }

        lytCartView.setOnClickListener {
          openMyCartScreen()
        }


        binding.lytFooterDetail.lytBuyNow.setOnClickListener {
            if (Prefences.getIsLogin(this@ProductDetailActivity)) {

                mData.apply {
                    MyCartResponse(
                        "" + product_id, "" + category_id, "" + sub_category_id,
                        "" + product_name, "" + weight_size, "" + main_price,
                        "" + display_price, "" + purchase_price, "" + display_price,
                        "" + description, "" + short_desp, "" + mImage,
                        "1", "" + gst, "" + category_name
                    ).let {
                        viewModel.insertItemInCart(it)
                        callAddToCartApi(it, true)
                    }
                }
            } else Utility.showToastForLogin(this@ProductDetailActivity)
        }

        binding.lytFooterDetail.lytAddToCart.setOnClickListener {
            if (Prefences.getIsLogin(this@ProductDetailActivity)) {

                binding.lytFooterDetail.lytPlusMinus.visibility = View.VISIBLE
                binding.lytFooterDetail.lytAddToCart.visibility = View.GONE
                binding.lytFooterDetail.lytGoToCart.visibility = View.VISIBLE
                binding.lytFooterDetail.lytBuyNow.visibility = View.GONE

                makeFlyAnimation(binding.lytFooterDetail.imageView)

                mData.apply {
                    MyCartResponse(
                        "" + product_id, "" + category_id, "" + sub_category_id,
                        "" + product_name, "" + weight_size, "" + main_price,
                        "" + display_price, "" + purchase_price, "" + display_price,
                        "" + description, "" + short_desp, "" + mImage,
                        "1", "" + gst, "" + category_name
                    ).let {
                        viewModel.insertItemInCart(it)
                        callAddToCartApi(it, false)
                    }
                }

            } else Utility.showToastForLogin(this@ProductDetailActivity)
        }

        binding.lytFooterDetail.lytGoToCart.setOnClickListener {

            openMyCartScreen()
        }

    }

    private fun callAddToCartApi(mData: ApiResponseModels.RelatedProductResponse.Success) {

        if(Prefences.getIsLogin(this@ProductDetailActivity)) {
            mViewModelCheckout.initAddToCart(
                Prefences.getUserId(this@ProductDetailActivity)!!,
                "" + mData.product_id, "1", true
            )!!
                .observe(this@ProductDetailActivity, Observer { mData ->
                    if (mData.status) {

                    }

                })
        }else Utility.showToastForLogin(this@ProductDetailActivity)

    }

    private fun callAddToCartApi(mData: MyCartResponse, isByuNow: Boolean) {
        if (Prefences.getIsLogin(this)) {
            mViewModelCheckout.initAddToCart(
                Prefences.getUserId(this@ProductDetailActivity)!!,
                "" + mData.product_id, "" + mData.qty, true
            )!!
                .observe(this@ProductDetailActivity, Observer { mData_ ->
                    if (mData_.status) {
                        if (isByuNow)
                            buyNow(mData.product_id)
                    } else
                        buyNow(mData.product_id)


                })
        } else Utility.showToastForLogin(this)

    }

    private fun buyNow(product_id: String) {

        Intent(this, MyCartActivity::class.java).apply {
            putExtra("type", "buyNow")
            putExtra("product_id", product_id)

        }.let {
            Utility.startActivityWithLeftToRightAnimation(this, it)
        }

    }

    private fun callMyWishList(productId: String) {

        viewModel.getMySignelWishList(productId).observe(this@ProductDetailActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                flag = false
                imgWishlist.setImageResource(R.drawable.ic_like_heart);
            } else {
                flag = true
                imgWishlist.setImageResource(R.drawable.ic_like_heart_unfilled);
            }

        })

    }


    private fun makeFlyAnimation(targetView: ImageView) {
        val destView = findViewById<View>(R.id.cartRelativeLayout) as LinearLayout
        CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(1000)
            .setDestView(destView).setAnimationListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    //addItemToCart()
//                    Toast.makeText(this@ProductDetailActivity, "Continue Shopping...", Toast.LENGTH_SHORT).show()
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            }).startAnimation()
    }

    private fun getMyCart(product_id: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.getMyCart(product_id).observe(this@ProductDetailActivity, Observer {
                if (it != null && it.isNotEmpty()) {

                    binding.lytFooterDetail.lytPlusMinus.visibility = View.VISIBLE
                    binding.lytFooterDetail.lytAddToCart.visibility = View.GONE
                    binding.lytFooterDetail.lytGoToCart.visibility = View.VISIBLE
                    binding.lytFooterDetail.lytBuyNow.visibility = View.GONE

                    list = it
                    binding.lytFooterDetail.txtItemValue.text = list[0].qty
                } else {
                    binding.lytFooterDetail.lytPlusMinus.visibility = View.GONE
                    binding.lytFooterDetail.lytAddToCart.visibility = View.VISIBLE
                    binding.lytFooterDetail.lytGoToCart.visibility = View.GONE
                    binding.lytFooterDetail.lytBuyNow.visibility = View.VISIBLE
                }

            })
        }
    }

    private fun getAllMyCart() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@ProductDetailActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    if (it.size > 0) {
                        flagCart = true
                        txtCartCount.text = "" + it.size
                    }
                } else {
                    flagCart = false
                    txtCartCount.text = "0"
                }
            })
        }
    }


    private fun callDetailAPi() {
        shimmerLayout.visibility = View.VISIBLE
        mViewModel.initProductDetail("" + product_id, false)!!.observe(this, Observer {
            shimmerLayout.visibility = View.GONE
            if (it.success != null) {
                imgWishlist.visibility = View.VISIBLE
                binding.lytFooterDetail.alpha = 1f
                binding.lytFooterDetail.isEnabled = true
                mData = it.success.product_detail
                if (it.success.multiimages != null)
                    mImage = it.success.multiimages[0]

                txtItemName.text = "" + it.success.product_detail.product_name
                txtCategory.text = "" + it.success.product_detail.category_name
                txtSKU.text = "" + it.success.product_detail.sku
                txtSize.text = "" + it.success.product_detail.weight_size
                txtSalePrice.text = "₹" + it.success.product_detail.display_price
                txtPrice.text = "₹" + it.success.product_detail.main_price


                it.success.product_detail.apply {
                    mMyCartData = ApiResponseModels.ProductListingResponse.Data(
                        "",
                        category_id,
                        description,
                        discount_percent,
                        display_price,
                        featured,
                        gfid,
                        qty,
                        gst,
                        hsn,
                        "" + mImage,
                        keyword,
                        main_price,
                        product_id,
                        product_name,
                        purchase_price,
                        qty,
                        short_desp,
                        sku,
                        slug,
                        "",
                        sub_category_id,
                        "" + mImage,
                        weight_size,
                        flag,
                        "",
                        minimum_price

                    )
                }

                if (it.success.product_detail.discount_percent != null && it.success.product_detail.discount_percent != "0" && it.success.product_detail.discount_percent != "") {
                    txtDiscount.visibility = View.VISIBLE
                    txtDiscount.text = "" + it.success.product_detail.discount_percent + "% Off"
                } else
                    txtDiscount.visibility = View.GONE


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    txtDescripton.setText(
//                        Html.fromHtml(
//                            "" + it.success.description,
//                            Html.FROM_HTML_MODE_COMPACT
//                        )
//                    );
//                } else {
//                    txtDescripton.setText(Html.fromHtml("" + it.success.description))
//                }

//                if (it.success.description==null || it.success.description=="")
//                    lytDesc.visibility=View.GONE
//                else
                lytDesc.visibility = View.VISIBLE

                if (it.success.multiimages.size > 0)
                    initPagerViewer(it.success.multiimages)

//                Glide.with(mContext).load(it.success.urlimage).into(productImage)

            } else Utility.showToast(mContext)


        })


        mViewModel.initRelatedProduct(product_id, false)!!.observe(this, Observer {
            if (it != null) {
                if (it.success != null && it.success.size > 0) {
                    binding.lytRelated.root.visibility = View.VISIBLE
                    initAdapter(it.success)
                } else {
                    binding.lytRelated.root.visibility = View.GONE
                }
            } else Utility.showToast(this)
        })

    }

    private fun initPagerViewer(images: List<String>) {

        NUM_PAGES = images.size
        var mList = java.util.ArrayList<ApiResponseModels.GroFiestaPageResponse.Success.Slider>()

        images.forEach {

            mList.add(
                ApiResponseModels.GroFiestaPageResponse.Success.Slider(
                    "", "", "", "", "",
                    "", "", "" + it, "", "", "",
                    "", ""
                )
            )

        }

        viewpager.adapter =
            BannerGroFiestaPagerAdapter(this@ProductDetailActivity, mList) {
                Intent(this@ProductDetailActivity, ImagePreviewActivity::class.java).apply {
                    putExtra("image", mList[it].urlimage)
                }.let {
                    Utility.startActivityWithLeftToRightAnimationContext(
                        this@ProductDetailActivity!!,
                        it
                    )
                }
            }
        if (mList.size > 1)
            indicator.setViewPager(viewpager)
    }


    private fun initAdapter(success: ArrayList<ApiResponseModels.RelatedProductResponse.Success>) {
        var horizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.lytRelated.rvRelated.layoutManager = horizontalLayout
        val mAdapter = ReleatedProductAdapter(success) {pos,type->
            when(type){
                "Detail"->openDetailPage(success[pos].product_id)
                "Add" -> if(Prefences.getIsLogin(this@ProductDetailActivity)) addToCart(success[pos]) else Utility.showToastForLogin(this@ProductDetailActivity)
                "GoTOCart" -> openMyCartScreen()
            }
        }
        binding.lytRelated.rvRelated.adapter = mAdapter
    }

    private fun openMyCartScreen() {

        Utility.startActivityWithLeftToRightAnimation(
            this@ProductDetailActivity,
            Intent(this@ProductDetailActivity, MyCartActivity::class.java)
        )

    }

    private fun addToCart(mData: ApiResponseModels.RelatedProductResponse.Success) {
        mData.apply {
            MyCartResponse(
                "" + product_id, "", "",
                "" + product_name, "" + weight_size, "" + main_price,
                "" + display_price, "", "" + display_price,
                "", "", "" + urlimage,
                "1", "", "test"
            ).let {
                viewModel.insertItemInCart(it)
            }
        }

        callAddToCartApi(mData)
    }


    private fun openDetailPage(product_id: String) {

        Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("product_id", product_id)
        }.let {
            Utility.startActivityWithLeftToRightAnimation(this, it)
        }
    }

    private fun openBottomSheet() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_detail, null)

//        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

//        btnClose.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.setCancelable(false)

        dialog.setContentView(view)

        dialog.show()
    }


    fun clickIncreaseQty(view: View) {
        viewModel.updateCartItemFromDetail(list[0], true)
        lytFooterDetail.txtItemValue.text = list[0].qty
        updateMyCart(mMyCartData!!, true)
    }

    fun clickDecreaseQty(view: View) {
        viewModel.updateCartItemFromDetail(list[0], false)
        lytFooterDetail.txtItemValue.text = list[0].qty
        updateMyCart(mMyCartData!!, false)
    }

    private fun updateMyCart(item: ApiResponseModels.ProductListingResponse.Data, b: Boolean) {

        item.apply {
            var _qty = lytFooterDetail.txtItemValue.text.toString().toInt()
            _qty = if (b) _qty + 1 else _qty - 1
            if (_qty >= 1) {
                mViewModel.initUpdateMyCart(
                    "" + item.product_id,
                    "" + Prefences.getUserId(this@ProductDetailActivity),
                    "" + _qty,
                    true
                )!!.observe(this@ProductDetailActivity, Observer { mData ->
                    if (mData.status) {
                    }
                })
            } else {
                mViewModel.initDeleteMyCart(
                    "" + item.product_id,
                    "" + Prefences.getUserId(this@ProductDetailActivity),
                    true
                )!!.observe(this@ProductDetailActivity, Observer { mData ->
                    if (mData.status) {
                    }
                })

            }
        }


    }

    fun clickWishList(view: View) {

        if (Prefences.getIsLogin(this)) {
            if (flag) {
                imgWishlist.setImageResource(R.drawable.ic_like_heart);
                flag = false

                mViewModel.initAddWishList(
                    "" + Prefences.getUserId(this@ProductDetailActivity),
                    "" + product_id, false
                )!!.observe(this, Observer {
                    if (it.status) {
                        mWid = "" + it.last_wishlist_id
                    } else Utility.showToast(mContext)
                })

                mData.apply {
                    MyCartResponse(
                        "" + product_id, "" + category_id, "" + sub_category_id,
                        "" + product_name, "" + weight_size, "" + main_price,
                        "" + display_price, "" + purchase_price, "" + display_price,
                        "" + description, "" + short_desp, "" + mImage,
                        "1", "" + gst, "" + category_name
                    ).let {
                        viewModel.insertItemInWishList(it)
                    }
                }

            } else {
                imgWishlist.setImageResource(R.drawable.ic_like_heart_unfilled)
                flag = true
                mViewModel.initRemoveWishList(
                    "" + mWid, false
                )!!.observe(this, Observer {
                    if (it.status) {

                    } else Utility.showToast(mContext)
                })

                viewModel.deleteItemFromWishList(mData.product_id)
            }
        } else Utility.showToastForLogin(this)
    }


}