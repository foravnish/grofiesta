package com.app.grofiesta.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback_from.*
import kotlinx.android.synthetic.main.app_header_layout.*

class ContactUsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Contact Us"


    }


}