package com.app.grofiesta.ui.main.view.product

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyOrderListAdapter
import com.app.grofiesta.adapter.SearchListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_search_product.*
import kotlinx.android.synthetic.main.activity_search_product.rvProductList

class SearchProductActivity : BaseActivity() {
    lateinit var mViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this)

        upButton.setOnClickListener { finish() }

        edtSearchText.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(edtSearchText, InputMethodManager.SHOW_IMPLICIT)

        edtSearchText.addTextChangedListener(afterTextChanged = {
            if (!it.toString().isEmpty()) {
                Handler().postDelayed(Runnable {
                    callSearchProduct(it.toString())
                }, 1000)


            }
        })

    }

    private fun callSearchProduct(srchTxt: String) {

        mViewModel.initSearchData("" + srchTxt, false)!!.observe(this, Observer {
            if (it.status != null) {

                if (it.data!=null && it.data.size>0){
                    rvProductList.visibility=View.VISIBLE
                    noDataFond.visibility=View.GONE
                    initAdapter(it.data)
                }else{
                    rvProductList.visibility=View.GONE
                    noDataFond.visibility=View.VISIBLE
                }

            }


        })
    }

    private fun initAdapter(mData: List<ApiResponseModels.ProductListingResponse.Data>) {
        rvProductList.layoutManager = LinearLayoutManager(this)
        val mAdapter = SearchListAdapter(mData) {
            Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("product_id", mData[it].product_id)
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this, it)
            }

        }
        rvProductList.adapter = mAdapter
    }

}