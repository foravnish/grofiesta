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
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.home_product_item.view.*
import java.lang.Exception

class AboutUsActivity : BaseActivity() {

    lateinit var mViewModel: HomeViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(HomeViewModel::class.java)
        mViewModel.init(this)

        imgBack.setOnClickListener { finish() }


        txtPageTitle.text = "About Us"

        callAboutUs()


    }

    private fun callAboutUs() {

        mViewModel.initAboutTnCPrivacy(true)!!.observe(this, Observer {
            if (it!=null){
                if (it.about != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtTitle.setText(
                            Html.fromHtml(
                                "" + it.about.title1,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        );
                    } else {
                        txtTitle.setText(Html.fromHtml("" + it.about.title1))
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtSubTitle.setText(
                            Html.fromHtml(
                                "" + it.about.sub_title,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        );
                    } else {
                        txtSubTitle.setText(Html.fromHtml("" + it.about.sub_title))
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtData.setText(
                            Html.fromHtml(
                                "" + it.about.desc1,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        );
                    } else {
                        txtData.setText(Html.fromHtml("" + it.about.desc1))
                    }

                    txtgrofiesta.text=""+it.about.grofiesta
                    txtproduse.text=""+it.about.produse

                    Glide.with(this).load(it.about.urlimage).into(imgAbt)

                }

            }

        })

    }


    override fun onBackPressed() {
        super.onBackPressed()

    }
}
