package com.app.grofiesta.ui.main.view.home


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.accountapp.accounts.base.BaseFragment
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.adapter.*
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.databinding.FragmentHomeBinding
import com.app.grofiesta.room.response.MyCartResponse
import com.app.grofiesta.ui.main.view.WebViewActivity
import com.app.grofiesta.ui.main.view.cart.CheckoutViewModel
import com.app.grofiesta.ui.main.view.cart.MyCartActivity
import com.app.grofiesta.ui.main.view.product.ProductDetailActivity
import com.app.grofiesta.ui.main.view.product.ProductViewModel
import com.app.grofiesta.ui.main.view.product.SearchProductActivity
import com.app.grofiesta.ui.main.view.product.ShopByGroAndFiestaActivity
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_deal_popup.*
import kotlinx.coroutines.*
import java.lang.Runnable


/**
 * A simple [Fragment] subclass.
 */

class HomeFragment : BaseFragment() {

    lateinit var mAdapter: HomeItemsAdapter
    val viewModel: GroceryViewModel by activityViewModels()
    lateinit var mViewModelCheckout: CheckoutViewModel

    companion object {
        val TAG = "FragmentHome"

        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    lateinit var binding: FragmentHomeBinding
    val mContext by lazy { context }
    var fragment: Fragment? = null

    internal var position: Int = 0
    internal var position2: Int = 0

    private var NUM_PAGES = 0
    private var NUM_PAGES2 = 0

    internal val PERIOD_MS: Long = 3 * 1000
    internal val PERIOD_MS2: Long = 3 * 1000
    private var handler = Handler()
    private var handler2 = Handler()

    lateinit var mViewModel: HomeViewModel
    lateinit var mViewModelProduct: ProductViewModel

    public override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(runnale)
//        handler2?.removeCallbacks(runnale2)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnale, PERIOD_MS)
//        handler2.postDelayed(runnale2, PERIOD_MS2)

    }

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(HomeViewModel::class.java)
        mViewModel.init(requireActivity())
        mViewModelCheckout = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(CheckoutViewModel::class.java)
        mViewModelCheckout.init(requireActivity())
        mViewModelProduct = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(ProductViewModel::class.java)
        mViewModelProduct.init(requireActivity())
        callBannerApi()


        callDynamicProducts()

//        callGroProducts()

        callMarqueeApi()


        binding.lnrsearch.setOnClickListener {
            Intent(requireActivity(), SearchProductActivity::class.java).apply {
            }.let {
                Utility.startActivityWithLeftToRightAnimation(requireActivity(), it)
            }

        }

        binding.lytups.clickGro.setOnClickListener {
            Intent(requireContext(), ShopByGroAndFiestaActivity::class.java).apply {
                putExtra("type", "Gro")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(requireActivity(), it)
            }

        }

        binding.lytups.clickFiesta.setOnClickListener {
            Intent(requireContext(), ShopByGroAndFiestaActivity::class.java).apply {
                putExtra("type", "Fiesta")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(requireActivity(), it)
            }

        }
        return binding.root;


    }

    private fun showDealPopup(image: String, url: String) {

        val mDialog = Utility.MyCustomDialog(mContext!!, R.layout.dialog_deal_popup)
        mDialog.imgClose.setOnClickListener {
            mDialog.dismiss()
        }
        mDialog.imgDeal.setOnClickListener {
            mDialog.dismiss()

        }
        Glide.with(this).load(image).into(mDialog.imgDeal)

        mDialog.imgDeal.setOnClickListener {
            Intent(requireActivity(), WebViewActivity::class.java).apply {
                putExtra("webUrl",""+url)
                putExtra("webTitle","Offers")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(requireActivity(),it)
            }

        }
        mDialog.show()

    }

    private fun callMarqueeApi() {

        mViewModel.initMarquee(true)!!.observe(requireActivity(), Observer {
            if (it!=null){
                if (it.status){
                    if (it.data!=null && it.data!="") {
                        binding.txtMarquee.visibility = View.VISIBLE
                        binding.txtMarquee.isSelected = true
                        binding.txtMarquee.text = "" + it.data


//                        val animator = ValueAnimator.ofFloat(1.0f, 0.0f).apply {
//                            this.repeatCount = ValueAnimator.INFINITE
//                            this.interpolator = LinearInterpolator()
//                            this.duration = 6000L
//                            this.addUpdateListener { animation ->
//                                val progress = animation.animatedValue as Float
//                                val width = binding.lytMarquee.width.toFloat()
//                                val translationX = width * progress
//                                binding.lytMarquee.translationX = translationX - width
////                                tv_dummy_text.translationX = translationX
//                            }
//                        }
//                        animator.start()
                    }else binding.txtMarquee.visibility=View.GONE
                }else
                    binding.txtMarquee.visibility=View.GONE
            }

        })

    }

    private fun callDynamicProducts() {

        mViewModel.initDymanicProducts(false)!!.observe(requireActivity(), Observer { mData ->
            binding.lytDynamicProduct.shimmerDymanicLayout.visibility = View.GONE
            if (mData != null) {
                if (mData.data != null && mData.data.size > 0) {
                    binding.lytDynamicProduct.lytDynamicProduct.visibility = View.VISIBLE
                    binding.lytDynamicProduct.root.visibility = View.VISIBLE


                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            withContext(lifecycleScope.coroutineContext) {

//                                for (i in 0..mData.success.size - 1) {
//                                    lifecycleScope.launchWhenStarted {
//                                        viewModel.getMyCart(mData.success[i].product_id)
//                                            .observe(requireActivity(), Observer {
//                                                if (it != null && it.isNotEmpty()) {
//                                                    mData.success[i].hasCart = true
//                                                }
//                                            })
//
//                                    }
//                                }

                            }
                        }

                        initAdapterDymanicProduct(mData.data,lifecycleScope,viewModel,requireActivity())

                    }


                } else {
                    binding.lytDynamicProduct.root.visibility = View.GONE
                }
            } else Utility.showToast(requireContext())
        })

    }

    private fun callBannerApi() {

        mViewModel.iniBannerAPi(false)!!.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.slider != null && it.slider.size > 0) {
                    initPagerViewer(it.slider)
                } else {
                    binding.lytGro.root.visibility = View.GONE
                }
                if (it.popup.image!=""){
                    showDealPopup(it.popup.image,it.popup.url)
                }
            } else Utility.showToast(requireContext())
        })

    }

    private fun callGroProducts() {
        mViewModel.initGroProducts(false)!!.observe(requireActivity(), Observer { mData ->
            binding.lytGro.shimmerLayout.visibility = View.GONE
            if (mData != null) {
                if (mData.success != null && mData.success.size > 0) {
                    binding.lytGro.lytGroData.visibility = View.VISIBLE
                    binding.lytGro.root.visibility = View.VISIBLE

                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            withContext(lifecycleScope.coroutineContext) {

                                for (i in 0..mData.success.size - 1) {
                                    lifecycleScope.launchWhenStarted {
                                        viewModel.getMyCart(mData.success[i].product_id)
                                            .observe(requireActivity(), Observer {
                                                if (it != null && it.isNotEmpty()) {
                                                    mData.success[i].hasCart = true
                                                }
                                            })

                                    }
                                }

//                                for (i in 0..mData.success.size - 1) {
//                                    lifecycleScope.launchWhenStarted {
//                                        viewModel.getMySignelWishList(mData.success[i].product_id)
//                                            .observe(requireActivity(), Observer {
//                                                if (it != null && it.isNotEmpty()) {
//                                                    mData.success[i].hasWishList = true
//                                                }
//                                            })
//
//
//                                    }
//                                }


                            }
                        }

                        initAdapter(mData.success)
                    }



                } else {
                    binding.lytGro.root.visibility = View.GONE
                }
            } else Utility.showToast(requireContext())
        })


/*
        mViewModel.initFiestaProducts(false)!!.observe(requireActivity(), Observer { mData ->
            binding.lytfiesta.shimmerLayout.visibility = View.GONE
            if (mData != null) {
                if (mData.success != null && mData.success.size > 0) {
                    binding.lytfiesta.lytFiestaData.visibility = View.VISIBLE
                    binding.lytfiesta.root.visibility = View.VISIBLE


                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            withContext(lifecycleScope.coroutineContext) {

                                for (i in 0..mData.success.size - 1) {
                                    lifecycleScope.launchWhenStarted {
                                        viewModel.getMyCart(mData.success[i].product_id)
                                            .observe(requireActivity(), Observer {
                                                if (it != null && it.isNotEmpty()) {
                                                    mData.success[i].hasCart = true
                                                }
                                            })

                                    }
                                }

                            }
                        }

                        initAdapter2(mData.success)
                    }


                } else {
                    binding.lytfiesta.root.visibility = View.GONE
                }
            } else Utility.showToast(requireContext())
        })
*/


//        mViewModel.initBestSellerProducts(false)!!.observe(requireActivity(), Observer {
//            if (it != null) {
//                if (it.success != null && it.success.size > 0) {
//                    binding.lytBestSeller.root.visibility = View.VISIBLE
//                    initAdapter3(it.success)
//                } else {
//                    binding.lytBestSeller.root.visibility = View.GONE
//                }
//            } else Utility.showToast(requireContext())
//        })


    }

    private fun initPagerViewer(mData: ArrayList<ApiResponseModels.BannerResponse.Success>) {

        NUM_PAGES = mData.size
        binding.viewpager.adapter = BannerPagerAdapter(requireContext(), mData){
            Intent(requireActivity(), WebViewActivity::class.java).apply {
                putExtra("webUrl",""+mData[it].heading)
                putExtra("webTitle",""+mData[it].title)
            }.let {
                Utility.startActivityWithLeftToRightAnimation(requireActivity(),it)
            }
        }
        binding.indicator.setViewPager(binding.viewpager)


    }

    private fun initAdapter(success: ArrayList<ApiResponseModels.GroProductResponse.Success>) {
        var horizontalLayout = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.lytGro.rvGro.layoutManager = horizontalLayout
        val mAdapter = HomeItemsAdapter("1", success,viewModel,requireActivity()) { pos, type ->
            when (type) {
                "Detail" -> openDetailPage(success[pos].product_id)
                "Add" -> if(Prefences.getIsLogin(requireActivity())) addToCart(success[pos]) else Utility.showToastForLogin(requireActivity())
                "GoTOCart" -> openMyCartScreen()
                "Wishlist" -> addWishlist(success[pos])
            }
        }
        binding.lytGro.rvGro.adapter = mAdapter
    }

    private fun addWishlist(
        mData: ApiResponseModels.GroProductResponse.Success
    ) {


        mViewModelProduct.initAddWishList(
            "" + Prefences.getUserId(requireActivity()),
            "" + mData.product_id, false
        )!!.observe(requireActivity(), Observer {
            if (it.status) {
            }
        })

        mData.apply {
            MyCartResponse(
                "" + product_id, "" , "" ,
                "" + product_name, "" + weight_size, "" + main_price,
                "" + display_price, "", "" + display_price,
                "", "", "" ,
                "1", "" , ""
            ).let {
                viewModel.insertItemInWishList(it)
            }
        }
    }

    private fun initAdapter2(success: ArrayList<ApiResponseModels.GroProductResponse.Success>) {
        var horizontalLayout = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.lytfiesta.rvFiesta.layoutManager = horizontalLayout
        val mAdapter = HomeItemsAdapter("1", success,viewModel,requireActivity()) { pos, type ->
            when (type) {
                "Detail" -> openDetailPage(success[pos].product_id)
                "Add" -> if(Prefences.getIsLogin(requireActivity())) addToCart(success[pos]) else Utility.showToastForLogin(requireActivity())
                "GoTOCart" -> openMyCartScreen()
                "Wishlist" -> addWishlist(success[pos])
            }

        }

        binding.lytfiesta.rvFiesta.adapter = mAdapter
    }


    private fun addToCart(mData: ApiResponseModels.GroProductResponse.Success) {
        mData.apply {
            var gstCal=(display_price.toDouble() * gst!!.toDouble())/100
            MyCartResponse(
                "" + product_id, "", "",
                "" + product_name, "" + weight_size, "" + main_price,
                "" + display_price, "", "" + display_price,
                "", "", "" + urlimage,
                "1", ""+gstCal, "test"
            ).let {
                viewModel.insertItemInCart(it)
            }
        }

        callAddToCartApi(mData)
    }

    private fun callAddToCartApi(mData: ApiResponseModels.GroProductResponse.Success) {

        if(Prefences.getIsLogin(requireActivity())) {
            mViewModelCheckout.initAddToCart(
                Prefences.getUserId(requireActivity())!!,
                "" + mData.product_id, "1", true
            )!!
                .observe(requireActivity(), Observer { mData ->
                    if (mData.status) {

                    }

                })
        }else Utility.showToastForLogin(requireActivity())

    }

    private fun openMyCartScreen() {

        Utility.startActivityWithLeftToRightAnimation(
            requireActivity(),
            Intent(requireActivity(), MyCartActivity::class.java)
        )

    }


    private fun openDetailPage(product_id: String) {

        Intent(requireActivity(), ProductDetailActivity::class.java).apply {
            putExtra("product_id", product_id)
        }.let {
            Utility.startActivityWithLeftToRightAnimation(requireActivity(), it)
        }
    }

    private fun initAdapterDymanicProduct(
        mData: List<ApiResponseModels.DymainHomeProductResponse.Data>,
        lifecycleScope: LifecycleCoroutineScope,
        viewModel: GroceryViewModel,
        requireActivity: FragmentActivity
    ) {
        var mLayout = LinearLayoutManager(requireActivity(),)
        binding.lytDynamicProduct.rvDynamicProduct.layoutManager = mLayout
        val mAdapter = HomePageDynamicAdapter(mData,lifecycleScope,viewModel,requireActivity) { mItem,productId,mType ->
            when(mType){
                "Detail" -> openDetailPage(productId)
                "Add" -> if(Prefences.getIsLogin(requireActivity())) addToCart(convertModel(mItem)) else Utility.showToastForLogin(requireActivity())
                "GoTOCart" -> openMyCartScreen()
            }
        }
        binding.lytDynamicProduct.rvDynamicProduct.adapter = mAdapter
    }

    private fun convertModel(mItem: ApiResponseModels.DymainHomeProductResponse.Data.Productsdata): ApiResponseModels.GroProductResponse.Success {

       var list= ApiResponseModels.GroProductResponse.Success(
            ""+mItem.product_id,""+mItem.product_name,""+mItem.weight_size,
            ""+mItem.main_price,""+mItem.display_price,""+mItem.display_price,
           ""+mItem.image,mItem.qty,mItem.gst,mItem.hasCart
        )
        return list
    }


    private fun initAdapter3(success: ArrayList<ApiResponseModels.BestSellerResponse.Success>) {
        var horizontalLayout = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.lytBestSeller.rvBestSeller.layoutManager = horizontalLayout
        val mAdapter = BestSellerAdapter(success) {
//            openDetailPage(success[it].product_id)
        }
        binding.lytBestSeller.rvBestSeller.adapter = mAdapter
    }




}
