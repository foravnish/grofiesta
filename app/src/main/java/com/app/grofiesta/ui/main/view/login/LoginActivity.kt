package com.app.grofiesta.ui.main.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    lateinit var mViewModel: LoginViewModel

    val mContext by lazy { this@LoginActivity }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this@LoginActivity)
    }

    fun clickSignUp(view : View){
        Utility.startActivityWithLeftToRightAnimation(
            this,
            Intent(this, SignupActivity::class.java)
        )

    }

    fun goToOtp(view : View){
        hideKeyboard()
        if (editMobileNumber.text.toString().isEmpty())
            showAlert("Please enter mobile number")
        else
            callLoginApi()

    }

    private fun callLoginApi() {
        mViewModel.getLogin( editMobileNumber.text.toString(), true)!!.observe(this, Observer {
            hideKeyboard()
            if (it.status) {
                if (it.data!=null){
                    Intent(this, OTPActivity::class.java).apply {
                        putExtra("mobile",editMobileNumber.text.toString().trim())
                        putExtra("customer_id",""+it.data.customer_id)
                    }.let {
                        Utility.startActivityWithLeftToRightAnimation(this,it)
                    }
                }

            } else
                showToast("Mobile number not Exist.")

        })

    }


}