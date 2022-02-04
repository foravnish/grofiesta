package com.app.grofiesta.ui.main.view.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyCartItemsListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityMyCartBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.address.ManageAddressActivity
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.ui.main.view.login.LoginActivity
import com.app.grofiesta.ui.main.view.product.ProductViewModel
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.main.app_header_layout.*

class MyCartActivity : BaseActivity() {
    lateinit var binding: ActivityMyCartBinding
    lateinit var mViewModelProduct: ProductViewModel

    val viewModel: GroceryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_cart)

        mViewModelProduct = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModelProduct.init(this)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "My Cart"

        bindAddress()

        getAllMyCart()

    }

    fun clickContinueShoping(view:View){
        var intent = Intent(this, HomeActivity::class.java)
        intent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        Utility.startActivityWithLeftToRightAnimation(this, intent)
    }

    private fun getAllMyCart() {
        mViewModelProduct.initMyCartListing(""+Prefences.getUserId(this),true)!!.observe(this, Observer {mData->
            if (mData.status){
                if (mData.data!=null && mData.data.size>0 ){

                    lytEmptyCart.visibility=View.GONE
                    nestedScroll.visibility=View.VISIBLE
                    lytFooter.visibility=View.VISIBLE
//                    var mTotalAmt = 0.0
//                    var qty=0
//                    for (mData in mData.data) {
//                        mTotalAmt = mTotalAmt + mData.totalAmount.toDouble()
//                        qty= qty + mData.qty.toInt()
//                    }
//                    txtItemTotal.text = "₹" + mTotalAmt
//                    txtToPayAmt.text=  "₹" + mTotalAmt
//                    txtTotalAmount.text=  "₹" + mTotalAmt
//                    txtPktQty.text=""+qty +" PKT"

                    initAdapter(mData.data)

                }else{
                    lytEmptyCart.visibility=View.VISIBLE
                    nestedScroll.visibility=View.GONE
                    lytFooter.visibility=View.GONE
                }
            }else{
                lytEmptyCart.visibility=View.VISIBLE
                nestedScroll.visibility=View.GONE
                lytFooter.visibility=View.GONE
            }


        })



        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@MyCartActivity, Observer {
                if (it != null && it.isNotEmpty()) {

                    var mTotalAmt = 0.0
                    var qty=0

                    for (mData in it) {
                        mTotalAmt = mTotalAmt + mData.totalAmount.toDouble()
                        qty= qty + mData.qty.toInt()
                    }
                    txtItemTotal.text = "₹" + mTotalAmt
                    txtToPayAmt.text=  "₹" + mTotalAmt
                    txtTotalAmount.text=  "₹" + mTotalAmt
                    txtPktQty.text=""+qty +" PKT"
                }

            })
        }

    }


    private fun initAdapter(list: List<ApiResponseModels.ProductListingResponse.Data>) {
        binding.rvCartList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyCartItemsListAdapter(list) { pos, item, type ->
            when (type) {
                "Plus" -> updateMyCart(item!!, true)
                "Minus" -> updateMyCart(item!!, false)
                "Delete" ->callDeleteMyCart(item.product_id,item.cart_id)

            }
        }
        binding.rvCartList.adapter = mAdapter
    }

    private fun updateMyCart(item: ApiResponseModels.ProductListingResponse.Data, b: Boolean) {

        viewModel.updateCartItem(item!!, b)

        item.apply {
            var _qty = qwantity.toInt()
            _qty = if (b) _qty + 1 else _qty - 1
            if (_qty >= 1){
                mViewModelProduct.initUpdateMyCart(""+item.cart_id,""+_qty,true)!!.observe(this@MyCartActivity, Observer {mData->
                    if (mData.status){
                        getAllMyCart()
                    }
                })
            } else {
                mViewModelProduct.initDeleteMyCart(""+cart_id,true)!!.observe(this@MyCartActivity, Observer {mData->
                    if (mData.status){
                        getAllMyCart()
                    }
                })

            }
        }



    }

    private fun callDeleteMyCart(productId: String,cart_id:String) {
        viewModel.deleteItemFromCart(productId)

        mViewModelProduct.initDeleteMyCart(""+cart_id,true)!!.observe(this, Observer {mData->
            if (mData.status){
                getAllMyCart()
            }
        })


    }


    fun clickCheckout(view: View) {
        if(Prefences.getIsLogin(this)) {
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, CheckoutActivity::class.java)
            )
        }else{
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, LoginActivity::class.java)
            )
        }
    }

    fun clickToAddChangeAddress(view: View) {

        if(Prefences.getIsLogin(this@MyCartActivity)) {
            Intent(this, ManageAddressActivity::class.java).apply {
            }.let {
                result.launch(it)
            }.also { initLeftRightTransaction() }
        }else{
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, LoginActivity::class.java)
            )

        }
    }

    var result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                bindAddress()
            }
        }

    override fun onRestart() {
        super.onRestart()

        bindAddress()
        getAllMyCart()
    }

    private fun bindAddress() {
        txtDeliveryAddress.text=""+Prefences.getAddress(this@MyCartActivity)
        txtDeliveryPincode.text="Pincode : "+Prefences.getPincode(this@MyCartActivity)

    }

}