package com.app.grofiesta.ui.main.view.product

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.grofiesta.R
import com.app.grofiesta.adapter.BannerGroFiestaPagerAdapter
import com.app.grofiesta.adapter.GroFiestaAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.ActivityShopByGroAndFiestaBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.activity_shop_by_gro_and_fiesta.*
import kotlinx.android.synthetic.main.app_header_layout.*

class ShopByGroAndFiestaActivity : BaseActivity() {
    lateinit var binding: ActivityShopByGroAndFiestaBinding
    var mType = ""

    internal var position: Int = 0
    private var NUM_PAGES = 0
    internal val PERIOD_MS: Long =
        3 * 1000 // time in milliseconds between successive task executions.
    private var handler = Handler()
    lateinit var mViewModel: ProductViewModel

    private val runnale = object : Runnable {
        override fun run() {
            binding.viewpager.setCurrentItem(position, true)
            if (position >= NUM_PAGES)
                position = 0
            else
                position++
            // Move to the next page after 3s
            handler.postDelayed(this, PERIOD_MS)
        }
    }

    public override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(runnale)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnale, PERIOD_MS)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_by_gro_and_fiesta)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@ShopByGroAndFiestaActivity)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Products"

        mType = intent.getStringExtra("type")!!
        if (mType == "Gro") {
            txtPageTitle.text = "Shop by Gro"
            txtType.text = "Gro Offers"
            txtHeaderType.text = "Gro"
            callGroPageApi()
        } else {
            txtPageTitle.text = "Shop by Fiesta"
            txtType.text = "Fiesta Offers"
            txtHeaderType.text = "Fiesta"
            initFiestaPage()
        }


    }


    private fun initFiestaPage() {
        mViewModel.initFiestaPage(true)!!.observe(this, Observer {
            if (it != null) {
                if (it.success != null && it.success.text != null) {
                    txtTitle.text = it.success.text.fiestatitle
                    txtData.text = it.success.text.fiesta
                    if (it.success.slider != null && it.success.slider.size > 0)
                        initPagerViewer(it.success.slider)
                    if (it.success.image!=null && it.success.image.size>0)
                        initAdapter(it.success.image)
                } else {

                }
            } else Utility.showToast(this)
        })

    }

    private fun callGroPageApi() {

        mViewModel.initGroPage(true)!!.observe(this, Observer {
            if (it != null) {
                if (it.success != null && it.success.text != null) {
                    txtTitle.text = it.success.text.grotitle
                    txtData.text = it.success.text.gro
                    if (it.success.slider != null && it.success.slider.size > 0)
                        initPagerViewer(it.success.slider)
                    if (it.success.image!=null && it.success.image.size>0)
                        initAdapter(it.success.image)
                } else {

                }
            } else Utility.showToast(this)
        })

    }

    private fun initPagerViewer(slider: List<ApiResponseModels.GroFiestaPageResponse.Success.Slider>) {

        NUM_PAGES = slider.size
        binding.viewpager.adapter =
            BannerGroFiestaPagerAdapter(this, slider)
        binding.indicator.setViewPager(binding.viewpager)

    }

    private fun initAdapter(image: List<ApiResponseModels.GroFiestaPageResponse.Success.Image>) {
        var horizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvGroAndFiesta.layoutManager = horizontalLayout
        val mAdapter = GroFiestaAdapter(image) {

        }
        binding.rvGroAndFiesta.adapter = mAdapter
    }


}