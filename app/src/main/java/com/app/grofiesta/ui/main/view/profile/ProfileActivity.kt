package com.app.grofiesta.ui.main.view.profile

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.data.model.request.UserProfileRequest
import com.app.grofiesta.databinding.ActivityProfileBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.login.LoginViewModel
import com.app.grofiesta.utils.Utility

class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding

    val mContext by lazy { this@ProfileActivity }

    lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this)

        callUserData()
    }

    private fun callUserData() {
        mViewModel.callUserDetail( Prefences.getUserId(this@ProfileActivity)!!, true)!!.observe(this, Observer {
            if (it.success!=null) {

                it.success.apply {
                    binding.editName.setText(firstname)
                    binding.editLastName.setText(lastname)
                    binding.editMobile.setText(telephone)
                    binding.editEmail.setText(email)
                }


            } else Utility.showToast(mContext)
        })

    }

    fun clickSave(view: View){
        callSaveProfileData()
    }

    private fun callSaveProfileData() {

        UserProfileRequest("","",
            "India",""+binding.editEmail.text.toString().trim(),
            ""+binding.editName.text.toString().trim(),""+binding.editLastName.text.toString().trim(),
            "").let {
            mViewModel.callSaveUserDetail( it, true)!!.observe(this, Observer {
                if (it.success==1) {


                } else Utility.showToast(mContext)
            })

        }

    }

}