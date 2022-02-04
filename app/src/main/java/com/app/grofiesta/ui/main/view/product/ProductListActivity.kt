package com.app.grofiesta.ui.main.view.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.adapter.ProductListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityProductListingBinding
import com.app.grofiesta.room.response.MyCartResponse
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductListActivity : BaseActivity() {
    lateinit var mViewModel: ProductViewModel
    lateinit var binding: ActivityProductListingBinding
    var sid=""
    var flag: Boolean = true
    var last_wishlist_id=""
    val viewModel: GroceryViewModel by viewModels()
    lateinit var mAdapter:ProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_listing)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Products"

        sid=intent.getStringExtra("sid")!!

        callDropDownProductList()

    }

    private fun callDropDownProductList() {
        mViewModel.initDropDownList(sid, false)!!.observe(this, Observer {mData->
            binding.shimmerLayout.visibility= View.GONE
            if (mData.status) {
                if (mData.data!=null && mData.data.size>0){
                    binding.rvProductList.visibility= View.VISIBLE
                    binding.noDataFond.visibility= View.GONE

                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            withContext(lifecycleScope.coroutineContext) {

                                for (i in 0..mData.data.size - 1) {
                                    lifecycleScope.launchWhenStarted {
                                        viewModel.getMySignelWishList(mData.data[i].product_id)
                                            .observe(this@ProductListActivity, Observer {
                                                if (it != null && it.isNotEmpty()) {
                                                    mData.data[i].hasWishList = true
                                                }
                                            })

                                    }
                                }
                            }
                        }

                        initAdapter(mData.data)
                    }


                }else{
                    binding.noDataFond.visibility= View.VISIBLE
                    binding.rvProductList.visibility= View.GONE
                }
            } else{
                binding.noDataFond.visibility= View.VISIBLE
                binding.rvProductList.visibility= View.GONE
            }
        })

    }

    private fun initAdapter(success: List<ApiResponseModels.ProductListingResponse.Data>) {
        binding.rvProductList.layoutManager = GridLayoutManager(this,2)
        mAdapter = ProductListAdapter(success) {pos,type->
            when(type){
                "Detail"->openDetailPage(success[pos].product_id)
                "Wishlist"-> addToWishList(success[pos])
            }


        }
        binding.rvProductList.adapter = mAdapter
    }

    private fun addToWishList(data: ApiResponseModels.ProductListingResponse.Data) {

        if(Prefences.getIsLogin(this)) {
        if (!data.hasWishList) {
            data.hasWishList = true
            mAdapter.notifyDataSetChanged()

            mViewModel.initAddWishList(""+ Prefences.getUserId(this@ProductListActivity),
                "" + data.product_id, false)!!.observe(this, Observer {
                if (it.status) {
                    last_wishlist_id=it.last_wishlist_id

                } else Utility.showToast(this@ProductListActivity)
            })

//            data.apply {
//                MyCartResponse(
//                    "" + product_id, "" + category_id, "" + sub_category_id,
//                    "" + product_name, "" + weight_size, "" + main_price,
//                    "" + display_price, "" + purchase_price, "" + display_price,
//                    "" + description, "" + short_desp, "" + urlimage,
//                    "1", "" + gst, ""+category_id
//                ).let {
//                    viewModel.insertItemInWishList(it)
//                }
//            }

        } else {
            data.hasWishList = false
            mAdapter.notifyDataSetChanged()
//            viewModel.deleteItemFromWishList(data.product_id)
            mViewModel.initRemoveWishList(
                "" + last_wishlist_id, false)!!.observe(this, Observer {
                if (it.status) {

                } else Utility.showToast(this@ProductListActivity)
            })

        }
        }else showToast("Please login first.")

    }

    private fun openDetailPage(product_id: String) {

        Intent(this@ProductListActivity, ProductDetailActivity::class.java).apply {
            putExtra("product_id",product_id)
        }.let {
            Utility.startActivityWithLeftToRightAnimation(this@ProductListActivity,it)
        }
    }


}