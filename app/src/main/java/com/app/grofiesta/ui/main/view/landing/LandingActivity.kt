package com.app.grofiesta.ui.main.view.landing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.grofiesta.R
import com.app.grofiesta.adapter.CustomPagerAdapter
import com.app.grofiesta.databinding.ActivityLandingBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.home.HomeActivity
import com.app.grofiesta.utils.Utility
import java.util.ArrayList

class LandingActivity : BaseActivity() {


    lateinit var binding: ActivityLandingBinding

    internal var images =
        intArrayOf(R.drawable.intro_img1, R.drawable.intro_img2, R.drawable.intro_img3)

//    val mViewModel by lazy { ViewModelProviders.of(mContext).get(LandingViewModel::class.java) }
    val mContext by lazy { this@LandingActivity }

    internal var txt: MutableList<String> = ArrayList()
    internal var txt2: MutableList<String> = ArrayList()

    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)

        txt.add("Welcome to the largest positive impact eco-friendly marketplace on the internet")
        txt.add("Welcome to the largest positive impact eco-friendly marketplace on the internet")
        txt.add("Welcome to the largest positive impact eco-friendly marketplace on the internet")

        txt2.add("Buy and Sell eco-friendly products\nOn I-Market")
        txt2.add("Buy eco-friendly products On I-Store")
        txt2.add("")


        binding.viewPager.setAdapter(
            CustomPagerAdapter(
                this@LandingActivity,
                images,
                txt as ArrayList<String>,
                txt2 as ArrayList<String>
            )
        )
        binding.indicator.setViewPager(binding.viewPager)

//        if (Prefences.getOnBoardingScreen(mContext)){
//            var intent=Intent(this,HomeActivity::class.java)
//            intent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            Utility.startActivityWithLeftToRightAnimation(this,intent)
    }

    fun onClickSkip(view:View) {
        var intent = Intent(this, HomeActivity::class.java)
        intent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        Utility.startActivityWithLeftToRightAnimation(this, intent)

    }

    fun onClickNext(view:View) {
        if (position == 0) {
            binding.viewPager.setCurrentItem(1)
            position = 1
        } else if (position == 1) {
            binding.viewPager.setCurrentItem(2)
            position = 2
        } else if (position == 2) {
            var intent = Intent(this, HomeActivity::class.java)
            intent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            Utility.startActivityWithLeftToRightAnimation(this, intent)
        }

    }
}

