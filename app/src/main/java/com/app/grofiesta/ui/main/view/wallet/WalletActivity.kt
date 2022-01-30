package com.app.grofiesta.ui.main.view.wallet

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyOrderListAdapter
import com.app.grofiesta.adapter.WalletHistorytAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityProductListingBinding
import com.app.grofiesta.databinding.ActivityWalletBinding
import com.app.grofiesta.databinding.ActivityWishListingBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.order.MyOrderViewModel
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_header_layout.*
import java.util.ArrayList

class WalletActivity : BaseActivity() {

    lateinit var binding: ActivityWalletBinding
    lateinit var mViewModel: WalletModel
    val mContext by lazy { this@WalletActivity }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(WalletModel::class.java)
        mViewModel.init(this)


        imgBack.setOnClickListener { finish() }

        txtPageTitle.text = "Wallet"

        callMyWallet()


    }

    private fun callMyWallet() {

        mViewModel.initMyWallet(Prefences.getUserId(this@WalletActivity)!!,true)!!.observe(this, Observer {
            if (it.data!=null) {
                if (it.data.current_balance!=null){
                    binding.apply {
                        txtCurrentBal.text="₹"+it.data.current_balance.wallet_value
                        txtCreditBal.text="₹"+it.data.credit
                        txtDebitBal.text="₹"+it.data.debit
                    }
                }

                if (it.data.history!=null && it.data.history.size>0){
                    txtLebel.visibility=View.VISIBLE
                    initAdapter(it.data.history)
                }else{
                    txtLebel.visibility=View.GONE
                }


            } else Utility.showToast(mContext)
        })

    }

    private fun initAdapter(history: ArrayList<ApiResponseModels.MyWallet.Data.History>) {

        binding.rvHistoryList.layoutManager = LinearLayoutManager(this)
        val mAdapter = WalletHistorytAdapter(history) {

        }
        binding.rvHistoryList.adapter = mAdapter
    }

}
