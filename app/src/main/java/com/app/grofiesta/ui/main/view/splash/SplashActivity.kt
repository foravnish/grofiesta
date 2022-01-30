package com.app.grofiesta.ui.main.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.grofiesta.R
import com.app.grofiesta.databinding.ActivitySplashBinding
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_signup.*

class SplashActivity : AppCompatActivity() , SplashHandler {

    lateinit var binding: ActivitySplashBinding

    internal var mSplashRepo = SplashRepository(this)
    val mContext by lazy { this@SplashActivity }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mSplashRepo.onSplashInitiated()

    }

    override fun onSplashCompleted() {

//        val isUserLoggedIn = Prefences.getIsLogin(mContext)

//        if (isUserLoggedIn)
//            Utility.startActivityWithLeftToRightAnimation(
//                this,
//                Intent(this, LoginActivity::class.java)
//            )
//
//        else
        Intent(this, HomeActivity::class.java).apply {
        }.let {
            Utility.startActivityWithLeftToRightAnimation(this,it)
        }
        finish()

    }

}