package com.app.grofiesta.ui.main.view.login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.data.model.request.SendOtpRequest
import com.app.grofiesta.room.response.MyCartResponse
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.ui.main.view.product.ProductViewModel
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.android.synthetic.main.activity_servcie.*
import kotlinx.android.synthetic.main.activity_wish_listing.*
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.app_header_layout.imgBack
import kotlinx.android.synthetic.main.app_header_layout.txtPageTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OTPActivity : BaseActivity() {

    lateinit var mViewModel: LoginViewModel
    lateinit var mViewModelProduct: ProductViewModel

    var mMobile=""
    var customer_id=""
    var otp=""
    val viewModel: GroceryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this)
        mViewModelProduct = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModelProduct.init(this@OTPActivity)
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


                    callWishListApi()

                }

            })

        }

    }

    private fun callWishListApi() {

        mViewModelProduct.initListWishList(""+ customer_id,
            true)!!.observe(this, Observer { mData->

            lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.IO) {
                    withContext(lifecycleScope.coroutineContext) {

                        if (mData.status){
                            if (mData.data!=null && mData.data.size>0 ){

                                mData.data.forEach { mList->
                                    mList.apply {
                                        MyCartResponse(
                                            "" + product_id, "" + category_id, "" + sub_category_id,
                                            "" + product_name, "" + weight_size, "" + main_price,
                                            "" + display_price, "" + purchase_price, "" + display_price,
                                            "" + description, "" + short_desp, "" + urlimage,
                                            "1", "" + gst, ""
                                        ).let {
                                            viewModel.insertItemInWishList(it)
                                        }
                                    }

                                }
                            }
                        }


                    }
                }


            }

        })

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
                                Prefences.setUserImage(this@OTPActivity,""+urlimage)
                                Prefences.setIsDeliveryBoy(this@OTPActivity,""+delevery_boy_status)

                            }
                            callMyCartListing(customer_id)


                        } else Utility.showToast(this)
                    })


                } else Utility.showToast(this)


            })

    }

    private fun callMyCartListing(customer_id:String) {

        mViewModelProduct.initMyCartListing(""+customer_id,true)!!.observe(this, Observer {mData->

            lifecycleScope.launchWhenStarted {
                withContext(Dispatchers.IO) {
                    withContext(lifecycleScope.coroutineContext) {

                        if (mData.status){
                            if (mData.data!=null && mData.data.size>0 ){

                                mData.data.forEach { mList->
                                    mList.apply {
                                        MyCartResponse(
                                            "" + product_id, "" + category_id, "" + sub_category_id,
                                            "" + product_name, "" + weight_size, "" + main_price,
                                            "" + display_price, "" + purchase_price, "" + display_price,
                                            "" + description, "" + short_desp, "" + urlimage,
                                            ""+qwantity, "" + gst, ""
                                        ).let {
                                            viewModel.insertItemInCart(it)
                                        }

                                    }

                                }
                            }
                        }


                    }
                }

                redirectToActivity()
            }


        })


    }

    private fun redirectToActivity() {

        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@OTPActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    Intent(this@OTPActivity, HomeActivity::class.java).apply {
                        putExtra("hasData","yes")
                    }.let {
                        Utility.startActivityWithLeftToRightAnimation(this@OTPActivity,it)
                    }
                    finishAffinity()
                }else{
                    Intent(this@OTPActivity, HomeActivity::class.java).apply {
                    }.let {
                        Utility.startActivityWithLeftToRightAnimation(this@OTPActivity,it)
                    }
                    finishAffinity()
                }

            })
        }

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



}
