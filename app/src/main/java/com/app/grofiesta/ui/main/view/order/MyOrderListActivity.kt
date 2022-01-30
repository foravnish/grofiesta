package com.app.grofiesta.ui.main.view.order

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyOrderDetailAdapter
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.adapter.MyOrderListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityOrderBinding
import com.app.grofiesta.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.app_header_layout.*

class MyOrderListActivity : BaseActivity() {
    lateinit var binding: ActivityOrderBinding
    lateinit var mViewModel: MyOrderViewModel
    val mContext by lazy { this@MyOrderListActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MyOrderViewModel::class.java)
        mViewModel.init(this)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "My Order"

        callMyOrder()
    }

    private fun callMyOrder() {
        binding.shimmerLayout.visibility=View.VISIBLE
        mViewModel.initMyOderList( Prefences.getUserId(this@MyOrderListActivity)!!, false)!!.observe(this, Observer {
            binding.shimmerLayout.visibility=View.GONE
            if (it.status) {
                if (it.data!=null && it.data.size>0){
                    binding.rvProductList.visibility= View.VISIBLE
                    binding.noDataFond.visibility= View.GONE
                    initAdapter(it.data)
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


    private fun initAdapter(success: ArrayList<ApiResponseModels.MyOderResponse.Success>) {
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyOrderListAdapter(success) {

            openBottomSheet(it)
        }
        binding.rvProductList.adapter = mAdapter
    }

    private fun openBottomSheet(it: Int) {

        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_detail, null)
        val rvOrderList = view.findViewById<RecyclerView>(R.id.rvOrderList)

        initOrderAdapter(rvOrderList)

//        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

//        btnClose.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.setCancelable(false)

        dialog.setContentView(view)

        dialog.show()
    }
    private fun initOrderAdapter(rvOrderList: RecyclerView) {
        rvOrderList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyOrderDetailAdapter() {

        }
        rvOrderList.adapter = mAdapter
    }


}