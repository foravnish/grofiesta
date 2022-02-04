package com.app.grofiesta.ui.main.view.deliveryBoy

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
import com.app.grofiesta.adapter.MyDeliveryListAdapter
import com.app.grofiesta.adapter.MyOrderDetailDeliveryBoyAdapter
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityOrderBinding
import com.app.grofiesta.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.dialog_change_status.*
import kotlinx.android.synthetic.main.dialog_delete.*
import kotlinx.android.synthetic.main.dialog_delete.btnNO
import kotlinx.android.synthetic.main.dialog_delete.btnYes

class DeliveryBoyListActivity : BaseActivity() {
    lateinit var binding: ActivityOrderBinding
    lateinit var mViewModel: DeliveryBoyViewModel
    val mContext by lazy { this@DeliveryBoyListActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(DeliveryBoyViewModel::class.java)
        mViewModel.init(this)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "My Delivery"

        callMyDelivery()
    }

    private fun callMyDelivery() {
        binding.shimmerLayout.visibility=View.VISIBLE
        mViewModel.initMyDeliveryList( Prefences.getUserId(this@DeliveryBoyListActivity)!!, false)!!.observe(this, Observer {
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


    private fun initAdapter(mData: List<ApiResponseModels.MyDeliveryResponse.Data>) {
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyDeliveryListAdapter(mData) { pos,type->
            when(type){
             "ChageStatus"->callPopupToChangeStatus(mData[pos])
             "Detail"->openBottomSheet(mData[pos],mData[pos].order_detail)
            }

        }
        binding.rvProductList.adapter = mAdapter
    }

    private fun callPopupToChangeStatus(data: ApiResponseModels.MyDeliveryResponse.Data) {

        val mDialog = Utility.MyCustomDialog(mContext!!, R.layout.dialog_change_status)
        mDialog.btn1.setOnClickListener {
            mDialog.dismiss()
            callChangeStatus(data,"1")
        }
        mDialog.btn2.setOnClickListener {
            mDialog.dismiss()
            callChangeStatus(data,"2")
        }
        mDialog.btn3.setOnClickListener {
            mDialog.dismiss()
            callChangeStatus(data,"3")
        }
        mDialog.show()


    }

    private fun callChangeStatus(data: ApiResponseModels.MyDeliveryResponse.Data,status:String) {


        mViewModel.initChageStatus( ""+data.order_id,""+data.customer_name,
            ""+data.customer_mobile,""+status,
            true)!!.observe(this, Observer {
            if (it.status) {
               // callMyDelivery()
            }
        })

    }

    private fun openBottomSheet(
        mData1: ApiResponseModels.MyDeliveryResponse.Data,
        mData: List<ApiResponseModels.MyDeliveryResponse.Data.OrderDetail>
    ) {

        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_detail, null)
        val rvOrderList = view.findViewById<RecyclerView>(R.id.rvOrderList)
        val txtOrderId = view.findViewById<TextView>(R.id.txtOrderId)
        val txtOrderDate = view.findViewById<TextView>(R.id.txtOrderDate)

        txtOrderId.text=""+mData1.customer_name
        txtOrderDate.text=""+mData1.customer_mobile

        initOrderAdapter(mData,rvOrderList)

//        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

//        btnClose.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.setCancelable(false)

        dialog.setContentView(view)

        dialog.show()
    }
    private fun initOrderAdapter(
        mData: List<ApiResponseModels.MyDeliveryResponse.Data.OrderDetail>,
        mDatarvOrderList: RecyclerView
    ) {
        mDatarvOrderList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyOrderDetailDeliveryBoyAdapter(mData) {

        }
        mDatarvOrderList.adapter = mAdapter
    }


}