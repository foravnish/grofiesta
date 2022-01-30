package com.app.grofiesta.ui.main.view.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.MyAddressAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_manage_address.*
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.dialog_delete.*

class ManageAddressActivity : BaseActivity() {
    lateinit var mViewModel: MyAddressViewModel
    val mContext by lazy { this }
    lateinit var mAdapter: MyAddressAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_address)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MyAddressViewModel::class.java)
        mViewModel.init(this)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Manage Address"

        callMyAddressList(false)

    }

    private fun callMyAddressList(isFlag:Boolean) {
        shimmerLayout.visibility = View.VISIBLE
        mViewModel.initMyAddressList(Prefences.getUserId(this@ManageAddressActivity)!!, isFlag)!!
            .observe(this, Observer {
                shimmerLayout.visibility = View.GONE
                if (it.status) {
                    if (it.data != null && it.data.size > 0) {

                        initAdapter(it.data)
                    }
                }
            })

    }

    private fun initAdapter(data: ArrayList<ApiResponseModels.MyAddressList.DataAddress>) {
        rvMyAddressList.layoutManager = LinearLayoutManager(this)
        mAdapter = MyAddressAdapter(data) { pos, type ->
            when (type) {
                "Edit" -> editAddressApi(data[pos])
                "Delete" -> openDeletePopup(data[pos].address_id)
                "Default" -> setDefaultAddress(data[pos])
            }

        }
        rvMyAddressList.adapter = mAdapter
    }

    private fun setDefaultAddress(dataAddress: ApiResponseModels.MyAddressList.DataAddress) {

        Prefences.setPincode(this@ManageAddressActivity, dataAddress.postcode)
        Prefences.setAddress(this@ManageAddressActivity, dataAddress.address)
        Prefences.setAddressId(this@ManageAddressActivity, dataAddress.address_id)
        mAdapter.notifyDataSetChanged()

        showToast("Default address changed.")

        Handler().postDelayed(Runnable {

            setResult(RESULT_OK)
            finish()

        }, 1000)

    }

    private fun openDeletePopup(address_id: String) {

        val mDialog = Utility.MyCustomDialog(mContext!!, R.layout.dialog_delete)
        mDialog.btnYes.setOnClickListener {
            mDialog.dismiss()
            deleteAddress(address_id)

        }
        mDialog.btnNO.setOnClickListener {
            mDialog.dismiss()
        }
        mDialog.show()


    }

    private fun deleteAddress(address_id: String) {
        mViewModel.initDeleteAddress(address_id, true)!!.observe(this, Observer {
            shimmerLayout.visibility = View.GONE
            if (it.status) {

                callMyAddressList(true)
            }
        })

    }

    private fun editAddressApi(data: ApiResponseModels.MyAddressList.DataAddress) {

        Intent(this@ManageAddressActivity, AddNewAddressActivity::class.java).apply {
            putExtra("data", data)
        }.let {
            result.launch(it)
        }.also { initLeftRightTransaction() }


    }

    var result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                callMyAddressList(true)
            }
        }

    fun clickAddAddress(view: View) {
        Intent(this@ManageAddressActivity, AddNewAddressActivity::class.java).apply {
        }.let {
            result.launch(it)
        }.also { initLeftRightTransaction() }


    }

}