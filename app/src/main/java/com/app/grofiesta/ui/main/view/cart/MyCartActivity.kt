package com.app.grofiesta.ui.main.view.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyCartItemsListAdapter
import com.app.grofiesta.databinding.ActivityMyCartBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.address.AddNewAddressActivity
import com.app.grofiesta.ui.main.view.address.ManageAddressActivity
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.ui.main.view.login.LoginActivity
import com.app.grofiesta.ui.main.view.product.ProductDetailActivity
import com.app.grofiesta.utils.Utility
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.main.app_header_layout.*

class MyCartActivity : BaseActivity() {
    lateinit var binding: ActivityMyCartBinding
    val viewModel: GroceryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_cart)

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

        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@MyCartActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    lytEmptyCart.visibility=View.GONE
                    nestedScroll.visibility=View.VISIBLE
                    lytFooter.visibility=View.VISIBLE
                    var mTotalAmt = 0.0
                    var qty=0

                    Log.d("MCartData", "" + Gson().toJson(it))
                    initAdapter(it)

                    for (mData in it) {
                        mTotalAmt = mTotalAmt + mData.totalAmount.toDouble()
                        qty= qty + mData.qty.toInt()
                    }
                    txtItemTotal.text = "₹" + mTotalAmt
                    txtToPayAmt.text=  "₹" + mTotalAmt
                    txtTotalAmount.text=  "₹" + mTotalAmt
                    txtPktQty.text=""+qty +" PKT"
                }else{

                    lytEmptyCart.visibility=View.VISIBLE
                    nestedScroll.visibility=View.GONE
                    lytFooter.visibility=View.GONE
                }

            })
        }
    }


    private fun initAdapter(list: List<MyCart>) {
        binding.rvCartList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyCartItemsListAdapter(list) { pos, item, type ->
            when (type) {
                "Plus" -> viewModel.updateCartItem(item!!, true)
                "Minus" -> viewModel.updateCartItem(item!!, false)
                "Delete" -> viewModel.deleteItemFromCart(item.product_id)
            }
        }
        binding.rvCartList.adapter = mAdapter
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