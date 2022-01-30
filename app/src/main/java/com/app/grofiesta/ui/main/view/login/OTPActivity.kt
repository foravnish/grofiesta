package com.app.grofiesta.ui.main.view.login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.data.model.request.SendOtpRequest
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.android.synthetic.main.app_header_layout.*


class OTPActivity : BaseActivity() {

    lateinit var mViewModel: LoginViewModel
    var mMobile=""
    var customer_id=""
    var otp=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = getString(R.string.verify_mobile)

        mMobile=intent.getStringExtra("mobile")!!
        customer_id=intent.getStringExtra("customer_id")!!
        callSendOtp()
        countDownOtp()


        if (otp_view.text!!.length == 6) {
            btnVerifyNow.alpha = 1f
            btnVerifyNow.isEnabled = true
            hideKeyboard()
        } else {
            btnVerifyNow.alpha = 0.7f
            btnVerifyNow.isEnabled = false
        }


        /*if (Utils.checkSMSPermission(this))
            OTP_Receiver().setEditText(otp_view)
        else
            Utils.requesPermisionForSms(this)*/

        otp_view.addTextChangedListener(afterTextChanged = {
            if (otp_view.text!!.length == 6) {
                btnVerifyNow.alpha = 1f
                btnVerifyNow.isEnabled = true
                hideKeyboard()
                verifyOtp()
            } else {
                btnVerifyNow.alpha = 0.7f
                btnVerifyNow.isEnabled = false
            }
        })

//        val client = SmsRetriever.getClient(this)
//        val task = client.startSmsRetriever()
//        task.addOnSuccessListener {
//            MySMSBroadcastReceiver.initSMSListener(object : SMSListener {
//
//                override fun onSuccess(message: String?) {
//                    if (message != null) {
//                        otp_view.setText(message)
//                        AppLog.printLog("OtpIsHere",message)
//                        // get the message here
//                    }
//                }
//
//                override fun onError(message: String?) {
//                    if (message != null)
//                        AppLog.printLog(message)
//                }
//            })
//        }
    }

    private fun callSendOtp() {
        SendOtpRequest(""+mMobile).let {
            mViewModel.initSendOtp( it, true)!!.observe(this, Observer {
                if (it.data!=null){
                    otp=""+it.data.opt
                }

            })

        }

    }

    private fun countDownOtp() {
        val timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtTimer.text = "Remain Sec "+((millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                txtResendOtp.isEnabled = true
                txtResendOtp.alpha = 1f
            }
        }
        timer.start()
    }

    fun gotToAccounts(view: View) {
        verifyOtp()
    }

    private fun verifyOtp() {

//        if (otp_view.text.toString().equals(otp)){
//            Intent(this, HomeActivity::class.java).apply {
//            }.let {
//                Utility.startActivityWithLeftToRightAnimation(this,it)
//            }
//            finish()
//        }else{
//            showToast("Please correct OTP.")
//        }

        mViewModel.verifyOtpApi(otp_view.text.toString(),otp_view.text.toString(),"",true)
            ?.observe(this, Observer {

                if (it.status) {

                    mViewModel.callUserDetail( customer_id, true)!!.observe(this, Observer {
                        if (it.success!=null) {

                            it.success.apply {
                                Prefences.setIsLogin(this@OTPActivity, true)
                                Prefences.setFirstName(this@OTPActivity,firstname)
                                Prefences.setLastName(this@OTPActivity,lastname)
                                Prefences.setUserMobile(this@OTPActivity,telephone)
                                Prefences.setUserEmailId(this@OTPActivity,email)
                                Prefences.setUserId(this@OTPActivity,customer_id)
                                Prefences.setPincode(this@OTPActivity,postcode)
                                Prefences.setAddress(this@OTPActivity,address)
                                Prefences.setAddressId(this@OTPActivity,""+address_id)

                            }

                            Intent(this, HomeActivity::class.java).apply {
//                                putExtra("mobile",editMobileNumber.text.toString().trim())
                            }.let {
                                Utility.startActivityWithLeftToRightAnimation(this,it)
                            }
                            finishAffinity()

                        } else Utility.showToast(this)
                    })


                } else Utility.showToast(this)


            })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //OTP_Receiver().setEditText(otp_view)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task =
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
    }

//     [END onActivityResult]
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account =
//                completedTask.getResult(ApiException::class.java)
//            AppLog.printLog(account!!.email.toString())
//            getGoogleLogin(account!!.email.toString())
//        } catch (e: ApiException) {
//            showToast("Something went wrong. Please Try Again...")
//            AppLog.printLog(
//                "signInResult:failed code=" + e.statusCode
//            )
//        }
//    }


    fun resendOtp(view: View) {
        SendOtpRequest(""+mMobile).let {
            mViewModel.initSendOtp( it, true)!!.observe(this, Observer {
                if (it.data!=null){
                    countDownOtp()
                    otp=""+it.data.opt
                }

            })

        }
    }

    fun clickToGmailLogin(view: View) {
//        try {
//            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build()
//            Utils.googleSingOut()
//            Utils.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//            val signInIntent: Intent = Utils.mGoogleSignInClient!!.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        } catch (e: Exception) {
//            println(e.message)
//        }
    }

    private fun getGoogleLogin(email: String) {
//        mViewModel1.getGoogleLogin(email)
//            ?.observe(this, Observer {
//                AppLog.printLog("Response", Gson().toJson(it))
//                val mResponse = JSONObject(it)
//                AppLog.printLog("MoModelIs", mResponse)
//                val hasErrors: Boolean = mResponse.getBoolean("hasErrors")
//                try {
//                    if (!hasErrors) {
//                        try {
//                            val myClass: ApiResponseModels.ResponsePassword = Gson().fromJson(
//                                mResponse.getString("response"),
//                                ApiResponseModels.ResponsePassword::class.java
//                            )
//                            mViewModelInsert.initInsertUser(myClass)
//                            getLoginLogout(myClass.token, myClass.user.empPartyACTBSr)
////                            val intent = Intent(this, MainActivity::class.java)
////                            intent.flags =
////                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                            startActivity(intent)
//                        } catch (e: Exception) {
//                        }
//                    } else {
//                        val error: String = mResponse.getString("errors")
//                        showToast(error)
//                    }
//                } catch (e: Exception) {
//                }
//            })
    }

    private fun getLoginLogout(token: String, userId: String) {
//        mViewModel.getLoginLogout(token, "4", userId)?.observe(this, Observer {
//            AppLog.printLog("ApiResponse", Gson().toJson(it))
//            if (!it.hasErrors) {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.flags =
//                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent, animBundleBTU())
//            } else {
//                showToast(it.errors)
//            }
//        })
    }
}
