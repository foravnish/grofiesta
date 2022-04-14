package com.app.grofiesta.ui.main.view.cart

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.adapter.MyCartItemsListCheckoutAdapter
import com.app.grofiesta.databinding.ActivityCheckoutBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.product.ProductDetailActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_header_layout.*
import com.app.grofiesta.R
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Window
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.ananda.retailer.Room.CanDatabase
import com.app.grofiesta.adapter.CouponListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.ui.main.view.login.LoginActivity
import com.app.grofiesta.ui.main.view.wallet.WalletModel
import com.app.grofiesta.utils.Utility.Companion.decimalTwoDigit
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_checkout.shimmerLayout
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog_coupon.*
import kotlinx.android.synthetic.main.dialog_deliver_once.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*

import kotlin.collections.ArrayList


class CheckoutActivity : BaseActivity(), PaymentResultListener {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var mViewModel: CheckoutViewModel
    lateinit var mViewModelWallet: WalletModel
    lateinit var d: Dialog

    val mContext by lazy { this@CheckoutActivity }
    val viewModel: GroceryViewModel by viewModels()
    var mShippingCharge = 0.0
    var mTotalAmt = 0.0
    var mGrandTotalAmt = 0.0
    var mGST = 0.0
    var mCoupon = 0.0
    var mWallet = 0.0
    var debit_amount=0.0
    var mSendWallet = 0.0
    var paymentStatus = "online"
    var isPayFromWallet: Boolean = false
    var mCouponName=""
    lateinit var mData: ArrayList<ApiResponseModels.CouponListResponse.Data>
    lateinit var mCartData: ArrayList<ApiResponseModels.AddToCart.Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(CheckoutViewModel::class.java)
        mViewModel.init(this)
        mViewModelWallet = ViewModelProvider.AndroidViewModelFactory(application)
            .create(WalletModel::class.java)
        mViewModelWallet.init(this)
        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Checkout"

        mCartData = ArrayList()
        mData= ArrayList()

        txtPayNow.alpha = 0.5f
        txtPayNow.isEnabled = false

        getAllMyCart()

        callGetShippingCharge()
        callMyWallet()

        getCoupons()

        edtCoupon.setOnClickListener {
            openBottomSheet(mData)
        }

        radio_group.setOnCheckedChangeListener(
            { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)

                if (radio.text == "Pay Online") {
                    txtPayNow.text = "Pay Now"
                    paymentStatus = "online"
//                    lytBalance.visibility = View.VISIBLE
                } else {
                    txtPayNow.text = "Buy Now"
                    paymentStatus = "cod"
//                    lytBalance.visibility = View.GONE

                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + 0.0
                    mGrandTotalAmt = amt1 - amt2
                    txtTotalAmount.text = "₹" + mGrandTotalAmt
                }
            })

        ckwallet.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                if (mGrandTotalAmt > mWallet) {
                    txtWallet.text = "- ₹ " + mWallet
                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + mWallet
                    mGrandTotalAmt = amt1 - amt2
                    txtGrandTotal.text = "₹" +Utility.twoDecimalDigit( mGrandTotalAmt.toString())
                    txtTotalAmount.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    debit_amount==0.0
                    mSendWallet=0.0
                    mGrandTotalAmt=mGrandTotalAmt-mWallet
                    txtPayNow.text = "Pay Now"
                } else {
                    txtWallet.text = "- ₹ " + mGrandTotalAmt
                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + mGrandTotalAmt
                    mGrandTotalAmt = amt1 - amt2
                    txtGrandTotal.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    txtTotalAmount.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    debit_amount==mGrandTotalAmt
                    mSendWallet=mWallet - mGrandTotalAmt
                    mGrandTotalAmt=0.0
                    txtPayNow.text = "Buy Now"
                }
                isPayFromWallet = true
            } else {
                var amt1 = mTotalAmt + mShippingCharge
                var amt2 = mCoupon + 0.0
                mGrandTotalAmt = amt1 - amt2
                txtGrandTotal.text = "₹" +Utility.twoDecimalDigit( mGrandTotalAmt.toString())
                txtTotalAmount.text = "₹" +Utility.twoDecimalDigit( mGrandTotalAmt.toString())
                txtWallet.text = "- ₹ 0.0"
                isPayFromWallet = false
            }
        }

    }

    private fun openBottomSheet(
        mData: ArrayList<ApiResponseModels.CouponListResponse.Data>
    ) {

        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_coupon, null)
        val rvCouponsList = view.findViewById<RecyclerView>(R.id.rvCouponsList)

        initCouponAdapter(mData, rvCouponsList,dialog)

        dialog.setContentView(view)

        dialog.show()
    }

    private fun initCouponAdapter(
        mData: ArrayList<ApiResponseModels.CouponListResponse.Data>,
        rvCouponsList: RecyclerView?,
        dialog: BottomSheetDialog
    ) {

        rvCouponsList!!.layoutManager = LinearLayoutManager(this)
        val mAdapter = CouponListAdapter(mData) {
            dialog.dismiss()
            mCouponName=mData[it].coupon_name
            edtCoupon.setText(""+mData[it].coupon_name)
        }
        rvCouponsList.adapter = mAdapter
    }


    private fun getCoupons() {
        mViewModel.initGetCouponList(true)!!
            .observe(this, Observer { mList ->
                if (mList.status) {
                    if (mList.data != null && mList.data.size > 0)
                        mData = mList.data
                }
            })


    }

    fun clickPayNow(view: View) {
        if (paymentStatus == "online") {
            if (isPayFromWallet) {
                if (mGrandTotalAmt > mWallet) {
                    debit_amount=0.0
                    mSendWallet=0.0
                    mGrandTotalAmt=mGrandTotalAmt-mWallet
                    gotoPaymentGatway(mGrandTotalAmt)
                } else {
                    debit_amount==mGrandTotalAmt
                    mSendWallet=mWallet - mGrandTotalAmt
                    mGrandTotalAmt=0.0
                    placeOrderNow()
                }
            } else
                gotoPaymentGatway(mGrandTotalAmt)

        } else placeOrderNow()

    }

    private fun callMyWallet() {
        mViewModelWallet.initMyWallet(Prefences.getUserId(this@CheckoutActivity)!!, true)!!
            .observe(this, Observer {
                if (it.data != null) {
                    if (it.data.current_balance != null) {
//                        if (it.data.current_balance.current_bal != "null") {
                        shimmerLayout.visibility = View.GONE
                        lytBalance.visibility = View.VISIBLE
                        txtPayNow.alpha = 1f
                        txtPayNow.isEnabled = true

                        binding.apply {
                            if (it.data.current_balance.wallet_value != "") {
                                mWallet = it.data.current_balance.wallet_value.toDouble()
                                txtwalletamt.text = "₹ " + mWallet + " Wallet balance"
                            } else {
                                mWallet = 0.0
                                txtwalletamt.text = "₹ " + mWallet + " Wallet balance"
                            }
                        }
//                        } else {
//                            showToast("Unauthorized user please login.")
//                            goForLogout()
//                        }

                    } else {
                        showToast("Unauthorized user please login.")
                        goForLogout()
                    }


                } else Utility.showToast(mContext)
            })

    }

    private fun goForLogout() {
        Prefences.resetUserData(this)
        CoroutineScope(Dispatchers.IO).launch {
            CanDatabase.INSTANCE?.clearAllTables()
        }

        Intent(this@CheckoutActivity, LoginActivity::class.java).apply {
        }.let {
            Utility.startActivityWithLeftToRightAnimation(this, it)
        }
        finishAffinity()

    }

    private fun getAllMyCart() {

        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@CheckoutActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    var qty = 0
                    it.forEach {
                        mCartData.add(
                            ApiResponseModels.AddToCart.Data(
                                "" + it.product_id,
                                "" + it.qty
                            )
                        )
                    }

                    initAdapter(it)

                    for (mData in it) {
                        mTotalAmt = (mTotalAmt + mData.totalAmount.toDouble())
                        if (mData.gst != "")
                            mGST = mGST + mData.gst.toDouble()

                        qty = qty + mData.qty.toInt()
                    }

                    var mWitoutGST=mTotalAmt-mGST
                    txtItemTotal.text = "₹" + Utility.twoDecimalDigit(mWitoutGST.toString())
                    txtGst.text = "+ ₹" + Utility.twoDecimalDigit(mGST.toString())
                    txtPktQty.text = "" + qty + " PKT"

                }

            })
        }
    }

    private fun callGetShippingCharge() {
        mViewModel.initShippingCharge(Prefences.getPincode(this@CheckoutActivity)!!, true)!!
            .observe(this, Observer {
                if (it.status) {
                    txtShipingCharge.text = "+ ₹" + it.shipping
                    mShippingCharge = it.shipping.toDouble()

                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + mWallet
                    mGrandTotalAmt = amt1 - amt2
                    txtGrandTotal.text = "₹" +Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    txtTotalAmount.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
//                    if (it.distance != null && it.distance != "") {
//                        if (it.distance.toInt() < 10)
//                            lytDistance.visibility = View.VISIBLE
//                        else
//                            lytDistance.visibility = View.GONE
//                    }
                } else {
                    mShippingCharge = 0.0
                    txtShipingCharge.text = "+ ₹" + mShippingCharge
                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + mWallet
                    mGrandTotalAmt = amt1 - amt2
                    txtGrandTotal.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    txtTotalAmount.text = "₹" +Utility.twoDecimalDigit( mGrandTotalAmt.toString())
                }
            })

    }

    private fun initAdapter(list: List<MyCart>) {
        binding.rvCartList.layoutManager = LinearLayoutManager(this)
        val mAdapter = MyCartItemsListCheckoutAdapter(list) {
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, ProductDetailActivity::class.java)
            )
        }
        binding.rvCartList.adapter = mAdapter
    }

    fun clickApplyCoupon(v: View) {

        if (edtCoupon.text.toString().isEmpty()) {
            showAlert("Please Select Coupon code.")
            return
        }
        hideKeyboard()
        callCoupan()
    }

    fun clickCloseCoupon(view: View) {
        binding.lytEditToApply.visibility = View.VISIBLE
        binding.lytApplyed.visibility = View.GONE
        mCoupon = 0.0
        txtDisCoupan.text = "₹ " + mCoupon
        txtDisCoupan.setTextColor(this.getColor(R.color.colorBlackLite))
        var amt1 = mTotalAmt + mShippingCharge
        var amt2 = mCoupon + mWallet
        mGrandTotalAmt = amt1 - amt2
        txtGrandTotal.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
        txtTotalAmount.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
    }

    private fun callCoupan() {
        mViewModel.initCoupon(
            edtCoupon.text.toString().trim(),
            Prefences.getUserId(this@CheckoutActivity)!!,
            true
        )!!.observe(this, Observer {
            if (it.status) {
                if (it.data != null) {
                    binding.lytApplyed.animate().rotationX(90f).setDuration(200)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                binding.lytApplyed.setRotationX(-90f)
                                binding.lytApplyed.animate().rotationX(0f).setDuration(200)
                                    .setListener(null)
                            }
                        })

                    binding.lytEditToApply.visibility = View.GONE
                    binding.lytApplyed.visibility = View.VISIBLE
                    binding.txtApplyed.text =
                        "" + edtCoupon.text.toString().trim() + " Applied Successfully."
                    binding.txtApplyed2.text = "₹" + it.data.coupon_val + " Off"

                    txtDisCoupan.text = "- ₹" + it.data.coupon_val
                    mCoupon = it.data.coupon_val.toDouble()
                    txtDisCoupan.setTextColor(this.getColor(R.color.green))

                    var amt1 = mTotalAmt + mShippingCharge
                    var amt2 = mCoupon + mWallet
                    mGrandTotalAmt = amt1 - amt2
                    txtGrandTotal.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                    txtTotalAmount.text = "₹" + Utility.twoDecimalDigit(mGrandTotalAmt.toString())
                } else
                    showSnackBar(binding.root, "Invalid coupon code.")
            } else
                showSnackBar(binding.root, "Invalid coupon code.")

        })

    }

    override fun onPaymentSuccess(p0: String?) {
        placeOrderNow()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        showToast("failed")
    }


    private fun gotoPaymentGatway(mToPayAmt: Double) {

        val amount = Math.round(mToPayAmt.toFloat() * 100)

        val checkout = Checkout()
        checkout.setKeyID("rzp_live_uNY256Lq96yKVU");
        checkout.setImage(R.drawable.logo)

        val obj = JSONObject()
        try {
            obj.put("name", "Grofiesta")
            obj.put("description", "")
            obj.put("theme.color", "")
            obj.put("currency", "INR")
            obj.put("amount", amount)
            obj.put("prefill.contact", "9717722161")
            obj.put("prefill.email", "connect@grofiesta.com")
            checkout.open(this@CheckoutActivity, obj)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun placeOrderNow() {
        mViewModel.initPlaceOrder(
            "" + Prefences.getUserId(this@CheckoutActivity)!!,
            "" + Prefences.getFirstName(this@CheckoutActivity)!!,
            "" + Prefences.getUserEmailId(this@CheckoutActivity)!!,
            "" + Prefences.getUserMobile(this@CheckoutActivity)!!,
            "" + mSendWallet,
            "" + paymentStatus,
            "1",
            "" + mShippingCharge,
            "" + mGST,
            "" + mGrandTotalAmt,
            "" + mCoupon,
            "" + Prefences.getPincode(this@CheckoutActivity),
            "" + Prefences.getAddress(this@CheckoutActivity),
            ""+debit_amount,
            true
        )!!
            .observe(this, Observer { mData ->
                if (mData.status) {

                    viewModel.deleteMyCartAll()
                    openDialogSucessfull(mGrandTotalAmt, mData.order_id)
                }else showAlert("Some Error! please try again.")
            })


    }

    fun openDialogSucessfull(mGrandTotalAmt: Double, order_id: String) {
        try {
            d = Dialog(this@CheckoutActivity)
            d!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            d.setCancelable(false)
            d.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            d.setContentView(R.layout.dialog_deliver_once)
            d.window!!.attributes.windowAnimations = R.style.DialogAnimation_UpDown_dialog
            val txtorderamt: TextView = d.findViewById<TextView>(R.id.txtorderamt)
            val txtorderid: TextView = d.findViewById<TextView>(R.id.txtorderid)

            txtorderamt.text = "Order Amt : INR " + mGrandTotalAmt
            txtorderid.text = "Order Id : " + order_id

            d.crossButton.setOnClickListener {

                Intent(this, HomeActivity::class.java).apply {
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this, it)
                }
                finishAffinity()

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) d.create()
            d.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}