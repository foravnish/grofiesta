package com.app.grofiesta.ui.main.view.product

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
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
import android.widget.Toast

import android.animation.Animator
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.RelativeLayout
import com.app.grofiesta.R
import com.app.grofiesta.utils.animUtil.CircleAnimationUtil


class ProductDetailActivity : BaseActivity() {
    lateinit var binding: ActivityProductDetailBinding
    var value = 1
    var flag: Boolean = true
    var product_id = ""
    lateinit var mViewModel: ProductViewModel
    val viewModel: GroceryViewModel by viewModels()
    var flagCart:Boolean=false
    val mContext by lazy { this@ProductDetailActivity }
    lateinit var list: List<MyCart>
    lateinit var mData: ApiResponseModels.ProductDetailResponse.Success
    var mImage= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@ProductDetailActivity)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Product Detail"
        product_id = intent.getStringExtra("product_id")!!

//        openBottomSheet()


        binding.lytFooter.alpha = 0.5f
        lytAddToCart.isEnabled = false

        callDetailAPi()

        callMyWishList(product_id)

        binding.productImage.setOnClickListener {
            Intent(this, ImagePreviewActivity::class.java).apply {
                putExtra("image", mImage)
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this, it)
            }

        }
        binding.lytFooter.setOnClickListener {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, MyCartActivity::class.java)
                )

        }

        lytCartView.setOnClickListener {
//            if (flagCart) {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, MyCartActivity::class.java)
                )
//            }
        }

        lytAddToCart.setOnClickListener {
            if (btnAddToCart.text == "Add To Cart") {
                btnAddToCart.text = "Go to Cart"
                lytPlusMinus.visibility=View.GONE
                makeFlyAnimation(imageView)
                mData.apply {
                    MyCartResponse(
                        "" + product_id, "" + category_id, "" + sub_category_id,
                        "" + product_name, "" + weight_size, "" + main_price,
                        "" + display_price, "" + purchase_price, "" + display_price,
                        "" + description, "" + short_desp, "" + urlimage,
                        "1", "" + gst, "" + category_name
                    ).let {
                        viewModel.insertItemInCart(it)
                    }
                }


            } else if (btnAddToCart.text == "Go to Cart") {
                lytPlusMinus.visibility=View.VISIBLE
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, MyCartActivity::class.java)
                )
            }
        }


    }

    private fun callMyWishList(productId: String) {

        viewModel.getMySignelWishList(productId).observe(this@ProductDetailActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                flag=false
                imgWishlist.setImageResource(R.drawable.ic_like_heart);
            }else{
                flag=true
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
                    btnAddToCart.text = "Go to Cart"
                    lytPlusMinus.visibility=View.VISIBLE
                    list=it
                    txtItemValue.text=list[0].qty
                }else{
                    btnAddToCart.text = "Add To Cart"
                    lytPlusMinus.visibility=View.GONE
                }

            })
        }
    }

    private fun getAllMyCart() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@ProductDetailActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    if (it.size > 0) {
                        flagCart=true
                        txtCartCount.text = "" + it.size
                    }
                }else {
                    flagCart=false
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
                imgWishlist.visibility=View.VISIBLE
                binding.lytFooter.alpha = 1f
                lytAddToCart.isEnabled = true
                mImage=it.success.urlimage
                mData = it.success

                txtItemName.text = "" + it.success.product_name
                txtCategory.text = "" + it.success.category_name
                txtSKU.text = "" + it.success.sku
                txtSize.text = "" + it.success.weight_size
                txtSalePrice.text = "₹" + it.success.display_price
                txtPrice.text = "₹" + it.success.main_price

                if (it.success.discount_percent!=null && it.success.discount_percent!="0"){
                    txtDiscount.visibility=View.VISIBLE
                    txtDiscount.text=""+it.success.discount_percent+"% Off"
                }else
                    txtDiscount.visibility=View.GONE


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDescripton.setText(
                        Html.fromHtml(
                            "" + it.success.description,
                            Html.FROM_HTML_MODE_COMPACT
                        )
                    );
                } else {
                    txtDescripton.setText(Html.fromHtml("" + it.success.description))
                }

                Glide.with(mContext).load(it.success.urlimage).into(productImage)

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


    private fun initAdapter(success: ArrayList<ApiResponseModels.RelatedProductResponse.Success>) {
        var horizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.lytRelated.rvRelated.layoutManager = horizontalLayout
        val mAdapter = ReleatedProductAdapter(success) {
            openDetailPage(success[it].product_id)
        }
        binding.lytRelated.rvRelated.adapter = mAdapter
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
        viewModel.updateCartItem(list[0], true)
        txtItemValue.text=list[0].qty
    }

    fun clickDecreaseQty(view: View) {
        viewModel.updateCartItem(list[0], false)
        txtItemValue.text=list[0].qty
    }

    fun clickWishList(view: View) {
        if (flag) {
            imgWishlist.setImageResource(R.drawable.ic_like_heart);
            flag = false

            mData.apply {
                MyCartResponse(
                    "" + product_id, "" + category_id, "" + sub_category_id,
                    "" + product_name, "" + weight_size, "" + main_price,
                    "" + display_price, "" + purchase_price, "" + display_price,
                    "" + description, "" + short_desp, "" + urlimage,
                    "1", "" + gst, "" + category_name
                ).let {
                    viewModel.insertItemInWishList(it)
                }
            }

        } else {
            imgWishlist.setImageResource(R.drawable.ic_like_heart_unfilled)
            flag = true
            viewModel.deleteItemFromWishList(mData.product_id)
        }
    }


    override fun onResume() {
        super.onResume()
        getAllMyCart()
        getMyCart(product_id)
    }
}