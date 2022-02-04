package com.app.grofiesta.ui.main.view.order

import android.os.Bundle
import android.view.View
import android.widget.TextView
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


    private fun initAdapter(mData: List<ApiResponseModels.OrderLIstingNewResponse.Data>) {
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyOrderListAdapter(mData) {

            openBottomSheet(mData[it].order_detail_data)
        }
        binding.rvProductList.adapter = mAdapter
    }

    private fun openBottomSheet(mDetailData: List<ApiResponseModels.OrderLIstingNewResponse.Data.OrderDetailData>) {

        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_detail, null)
        val rvOrderList = view.findViewById<RecyclerView>(R.id.rvOrderList)
        val txtOrderId = view.findViewById<TextView>(R.id.txtOrderId)
        val txtOrderDate = view.findViewById<TextView>(R.id.txtOrderDate)

        txtOrderId.text="Order Id: #"+mDetailData[0].order_id
        txtOrderDate.text="Date: "+mDetailData[0].date_added
        initOrderAdapter(rvOrderList,mDetailData)

//        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

//        btnClose.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.setCancelable(false)

        dialog.setContentView(view)

        dialog.show()
    }
    private fun initOrderAdapter(
        rvOrderList: RecyclerView,
        mDetailData: List<ApiResponseModels.OrderLIstingNewResponse.Data.OrderDetailData>
    ) {
        rvOrderList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyOrderDetailAdapter(mDetailData) {

        }
        rvOrderList.adapter = mAdapter
    }


}