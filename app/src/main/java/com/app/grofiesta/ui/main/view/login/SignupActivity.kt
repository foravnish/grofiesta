package com.app.grofiesta.ui.main.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.grofiesta.R
import com.app.grofiesta.databinding.*
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import com.app.grofiesta.utils.Utility.Companion.showToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.editMobileNumber

class SignupActivity : BaseActivity() {

    lateinit var mViewModel: LoginViewModel
    val mContext by lazy { this@SignupActivity }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this@SignupActivity)
    }

    fun goToOtp(view : View){
        hideKeyboard()
        if (editName.text.toString().isEmpty())
            showAlert("Enter First Name")
        else if (editLastName.text.toString().isEmpty())
            showAlert("Enter Last Name")
        else if (editEmail.text.toString().isEmpty())
            showAlert("Enter Email id")
        else if (editMobileNumber.text.toString().isEmpty())
            showAlert("Enter Mobile Number")
        else if (editAddress.text.toString().isEmpty())
            showAlert("Enter Address")
        else if (editPostCode.text.toString().isEmpty())
            showAlert("Enter Pin code")
        else
            callSignUpAPi()
    }

    private fun callSignUpAPi() {
        mViewModel.getRegistration(
            ""+editName.text.toString().trim(),
            ""+editLastName.text.toString().trim(),
            ""+editEmail.text.toString().trim(),
            ""+editMobileNumber.text.toString().trim(),
            ""+editAddress.text.toString().trim(),
            ""+editPostCode.text.toString().trim(),
            true)!!.observe(this, Observer {
            if (it.status) {

                Intent(this, OTPActivity::class.java).apply {
                    putExtra("mobile",editMobileNumber.text.toString().trim())
                    putExtra("customer_id",""+it.userid)
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this,it)
                }

            } else showToast(mContext)
        })



    }


}