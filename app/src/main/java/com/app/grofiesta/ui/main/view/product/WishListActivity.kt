package com.app.grofiesta.ui.main.view.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ananda.retailer.Room.Tables.MyWishList
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.adapter.WishListAdapter
import com.app.grofiesta.databinding.ActivityWishListingBinding
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_wish_listing.*
import kotlinx.android.synthetic.main.app_header_layout.*

class WishListActivity : BaseActivity() {
    lateinit var binding: ActivityWishListingBinding
    val viewModel: GroceryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wish_listing)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Wishlist"

        getMyWishList()

    }

    private fun getMyWishList() {

        viewModel.getMySignelWishList().observe(this@WishListActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                rvProductList.visibility=View.VISIBLE
                noDataFond.visibility=View.GONE
                initAdapter(it)
            }else{
                rvProductList.visibility=View.GONE
                noDataFond.visibility=View.VISIBLE
            }

        })

    }


    private fun initAdapter(list: List<MyWishList>) {
        binding.rvProductList.layoutManager = GridLayoutManager(this,2)
        val mAdapter = WishListAdapter(list) { pos,type->
            when(type){
                "detail"->openDetailPage(list[pos].product_id)
                "close"-> deleteWishlist(list[pos].product_id)
            }
        }
        binding.rvProductList.adapter = mAdapter
    }

    private fun deleteWishlist(productId: String) {
        viewModel.deleteItemFromWishList(productId)
        getMyWishList()

    }

    private fun openDetailPage(productId: String) {
        Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("product_id",productId)
        }.let {
            Utility.startActivityWithLeftToRightAnimation(this,it)
        }


    }

}