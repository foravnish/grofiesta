package com.app.grofiesta.ui.main.view.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.activity_manage_address.*
import kotlinx.android.synthetic.main.app_header_layout.*

class AddNewAddressActivity : BaseActivity() {
    lateinit var mViewModel: MyAddressViewModel
    val mContext by lazy { this }
    var address_id = ""
    lateinit var mData: ApiResponseModels.MyAddressList.DataAddress
    var flag: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MyAddressViewModel::class.java)
        mViewModel.init(this)

        if (intent.hasExtra("data")) {
            flag = false
            txtPageTitle.text = "Edit Address"
            mData =
                intent.getSerializableExtra("data") as ApiResponseModels.MyAddressList.DataAddress
            editAddress.setText("" + mData.address.toString())
            editPincode.setText("" + mData.postcode.toString())
            address_id = mData.address_id

        } else {
            flag = true
            txtPageTitle.text = "Add Address"
        }

        imgBack.setOnClickListener { finish() }


    }

    fun goToSubmit(view: View) {

        if (isValidate()) {
            if (flag) {
                mViewModel.initAddNewAddress(
                    "" + editPincode.text.toString(), "" + editAddress.text.toString(),
                    "" + Prefences.getUserId(mContext), true
                )!!.observe(this, Observer {
                    if (it.status) {
                        showToast("Address added successfully.")
                        setResult(RESULT_OK)
                        finish()
                    }
                })
            } else {
                mViewModel.initUpdateAddress(
                    "" + editPincode.text.toString(),
                    "" + editAddress.text.toString(), "" + address_id, true
                )!!.observe(this, Observer {
                    if (it.status) {
                        showToast("Address updated successfully.")
                        setResult(RESULT_OK)
                        finish()
                    }
                })

            }
        }

    }

    private fun isValidate(): Boolean {

        if (editAddress.text.toString().isEmpty()) {
            showAlert("Please enter Address.")
            return false
        }
        if (editPincode.text.toString().isEmpty()) {
            showAlert("Please enter Pincode.")
            return false
        }
        return true

    }


}