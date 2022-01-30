package com.app.grofiesta

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_tnc.*
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_header_layout.*
import java.lang.Exception

class TermsConditionsActivity : BaseActivity() {

    var mTitle = ""
    lateinit var mViewModel: HomeViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tnc)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(HomeViewModel::class.java)
        mViewModel.init(this)

        imgBack.setOnClickListener { finish() }


        mTitle = intent.getStringExtra("mTitle")!!
        txtPageTitle.text = mTitle
//        txtHeaderType.text= mTitle

        callAPi()


    }

    private fun callAPi() {

        mViewModel.initAboutTnCPrivacy(true)!!.observe(this, Observer {
            if (it!=null){
                if (mTitle=="Terms & Conditions") {
                    if (it.tnc != null) {
                        if (it.privecy_policy != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                txtData.setText(
                                    Html.fromHtml(
                                        "" + it.tnc.description,
                                        Html.FROM_HTML_MODE_COMPACT
                                    )
                                );
                            } else {
                                txtData.setText(Html.fromHtml("" + it.tnc.description))
                            }

                        }

                    }
                }else if(mTitle=="Privacy Policy"){
                    if (it.privecy_policy != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtData.setText(
                                Html.fromHtml(
                                    "" + it.privecy_policy.description,
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            );
                        } else {
                            txtData.setText(Html.fromHtml("" + it.privecy_policy.description))
                        }

                    }
                }

            }

        })

    }


    override fun onBackPressed() {
        super.onBackPressed()

    }
}
