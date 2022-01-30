package com.app.grofiesta.ui.main.view.offers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.grofiesta.R
import com.app.grofiesta.adapter.OffersListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityOffersBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.product.ImagePreviewActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_header_layout.*

class OffersActivity : BaseActivity() {

    lateinit var mViewModel:OfferViewModel
    val mContext by lazy { this@OffersActivity }
    lateinit var binding: ActivityOffersBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offers)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(OfferViewModel::class.java)
        mViewModel.init(this)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Offers"


        callOffer()
    }

    private fun callOffer() {
        binding.shimmerLayout.visibility=View.VISIBLE
        mViewModel.iniOfferList( false)!!.observe(this, Observer {
            binding.shimmerLayout.visibility= View.GONE
            if (it.success!=null) {
                if (it.success!=null && it.success.size>0){
                    binding.rvOffersList.visibility= View.VISIBLE
                    binding.noDataFond.visibility= View.GONE
                    initAdapter(it.success)
                }else{
                    binding.noDataFond.visibility= View.VISIBLE
                    binding.rvOffersList.visibility= View.GONE
                }

            } else Utility.showToast(mContext)
        })

    }

    private fun initAdapter(success: ArrayList<ApiResponseModels.OffersResponse.Success>) {

        rvOffersList.layoutManager = GridLayoutManager(this,2)
        val mAdapter = OffersListAdapter(success) {
            Intent(this, ImagePreviewActivity::class.java).apply {
                putExtra("image", success[it].urlimage)
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this, it)
            }

        }
        rvOffersList.adapter = mAdapter

    }

}
