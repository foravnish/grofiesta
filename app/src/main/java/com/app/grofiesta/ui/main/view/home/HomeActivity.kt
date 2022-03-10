package com.app.grofiesta.ui.main.view.home

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Room.CanDatabase
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.AboutUsActivity
import com.app.grofiesta.R
import com.app.grofiesta.TermsConditionsActivity
import com.app.grofiesta.data.api.ApiUrls
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.FeedbackFromActivity
import com.app.grofiesta.ui.main.view.WebViewActivity
import com.app.grofiesta.ui.main.view.address.ManageAddressActivity
import com.app.grofiesta.ui.main.view.cart.MyCartActivity
import com.app.grofiesta.ui.main.view.deliveryBoy.DeliveryBoyListActivity
import com.app.grofiesta.ui.main.view.login.LoginActivity
import com.app.grofiesta.ui.main.view.offers.OffersActivity
import com.app.grofiesta.ui.main.view.order.MyOrderListActivity
import com.app.grofiesta.ui.main.view.product.ProductDropDownListing
import com.app.grofiesta.ui.main.view.product.ServiceActivity
import com.app.grofiesta.ui.main.view.product.WishListActivity
import com.app.grofiesta.ui.main.view.profile.ProfileActivity
import com.app.grofiesta.ui.main.view.wallet.WalletActivity
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.txtCartCount
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.navigation_footer_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URLEncoder


class HomeActivity : BaseActivity() {
    val viewModel: GroceryViewModel by viewModels()
    lateinit var mViewModel: HomeViewModel

    var flagCart:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(HomeViewModel::class.java)
        mViewModel.init(this)

        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottom_navigation.setItemIconTintList(null);

        openFragment(HomeFragment.newInstance())

        setNavFooterLayoutUtilities()

        if (intent.hasExtra("hasData")){
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, MyCartActivity::class.java)
            )
        }
        btnMyCart.setOnClickListener {
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, MyCartActivity::class.java)
            )
        }

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }


        getAllMyCart()

    }


    private fun setNavFooterLayoutUtilities() {

        if (!Prefences.getIsLogin(this)){
            navServcie.visibility=View.GONE
            navMyOrder.visibility=View.GONE
            navWallet.visibility=View.GONE
            navAddress.visibility=View.GONE
            navProfile.visibility=View.GONE
            navFeedback.visibility=View.GONE
            navWishlist.visibility=View.GONE
            v1.visibility=View.GONE
            v2.visibility=View.GONE
            v3.visibility=View.GONE
            v4.visibility=View.GONE
            v5.visibility=View.GONE
            v6.visibility=View.GONE
            v7.visibility=View.GONE

            txtLoginOrLogout.text="Login"
        }else{
            txtLoginOrLogout.text="Logout"
            if (Prefences.getIsDeliveryBoy(this)=="1"){
                navDelBoy.visibility=View.VISIBLE
                v7.visibility=View.VISIBLE
            }
        }
        navDelBoy.setOnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, DeliveryBoyListActivity::class.java).apply {
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        }
        navHome.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, HomeActivity::class.java).apply {
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })

        navCart.setOnClickListener(View.OnClickListener {
            closeDrawer()

            Intent(this@HomeActivity, MyCartActivity::class.java).apply {
//                putExtra("type","Gro")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }
        })


        navGro.setOnClickListener(View.OnClickListener {
            closeDrawer()

            Intent(this@HomeActivity, ProductDropDownListing::class.java).apply {
                putExtra("type","Gro")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })

        navFiesta.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, ProductDropDownListing::class.java).apply {
                putExtra("type","Fiesta")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }


        })

        navServcie.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, ServiceActivity::class.java).apply {
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })

        navShare.setOnClickListener {
            try {
                val Sharetxt =
                    """I recommend to download Grofiesta App. Your online Grocery app. Download now:

Android:
https://play.google.com/store/apps/details?id=${packageName}
 """
                shareText("Ananda", Sharetxt)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        navFeedback.setOnClickListener {
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, FeedbackFromActivity::class.java)
            )

        }

        navProfile.setOnClickListener(View.OnClickListener {
            closeDrawer()

            if (Prefences.getIsLogin(this)) {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, ProfileActivity::class.java)
                )
            }else{
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, LoginActivity::class.java)
                )
            }

        })

        navMyOrder.setOnClickListener(View.OnClickListener {
            closeDrawer()

            if (Prefences.getIsLogin(this)) {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, MyOrderListActivity::class.java)
                )
            }else{
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, LoginActivity::class.java)
                )
            }

        })
        navAddress.setOnClickListener(View.OnClickListener {
            closeDrawer()
            if (Prefences.getIsLogin(this)) {
                Intent(this@HomeActivity, ManageAddressActivity::class.java).apply {
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this,it)
                }
            }else{
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, LoginActivity::class.java)
                )
            }

        })
        navWallet.setOnClickListener(View.OnClickListener {
            closeDrawer()
            if (Prefences.getIsLogin(this)) {
                Intent(this@HomeActivity, WalletActivity::class.java).apply {
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this,it)
                }
            }else{
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, LoginActivity::class.java)
                )
            }


        })
        navOffers.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, OffersActivity::class.java).apply {
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })


        navAboutUs.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, AboutUsActivity::class.java).apply {
//                Intent(this@HomeActivity, WebViewActivity::class.java).apply {
//                    putExtra("webUrl","https://nayapatta.com/grofiesta/welcome/about")
//                    putExtra("webTitle","About Us")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })
        navWishlist.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, WishListActivity::class.java)
            )

        })

        navTnc.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, TermsConditionsActivity::class.java).apply {
                putExtra("mTitle","Terms & Conditions")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })
        navPrivacyPolicy.setOnClickListener(View.OnClickListener {
            closeDrawer()
            Intent(this@HomeActivity, TermsConditionsActivity::class.java).apply {
                putExtra("mTitle","Privacy Policy")
            }.let {
                Utility.startActivityWithLeftToRightAnimation(this,it)
            }

        })
        navLogout.setOnClickListener(View.OnClickListener {
            closeDrawer()

            if (Prefences.getIsLogin(this)) {
                val alertDialog = AlertDialog.Builder(this@HomeActivity)
                alertDialog.setTitle("")
                alertDialog.setMessage("Are you sure want to Logout? ")
                alertDialog.setPositiveButton("Yes") { dialog, which ->

                    dialog.dismiss()

                    Prefences.resetUserData(this)
                    CoroutineScope(Dispatchers.IO).launch {
                        CanDatabase.INSTANCE?.clearAllTables()
                    }

                    Intent(this@HomeActivity, LoginActivity::class.java).apply {
                    }.let {
                        Utility.startActivityWithLeftToRightAnimation(this,it)
                    }
                    finishAffinity()

                }
                alertDialog.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }else {
                Intent(this@HomeActivity, LoginActivity::class.java).apply {
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this, it)
                }
                finishAffinity()
            }


        })

        navFb.setOnClickListener(View.OnClickListener {
            callContactUs("fb")
        })

        navInsta.setOnClickListener(View.OnClickListener {
            callContactUs("insta")
        })

        navWhatsApp.setOnClickListener(View.OnClickListener {
            callContactUs("wp")
        })

        navContWapp.setOnClickListener(View.OnClickListener {
            callContactUs("wp")
        })
        navCallGrofiesta.setOnClickListener(View.OnClickListener {
            callContactUs("call")
        })

        lytCartView.setOnClickListener {
//            if (flagCart) {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, MyCartActivity::class.java)
                )
//            }
        }

        txtAppVersion.text = "v" + Utility.getAppVersion()

        val headerView = nav_view!!.getHeaderView(0)
        headerView.view_container.setOnClickListener(View.OnClickListener {
            closeDrawer()
            if (Prefences.getIsLogin(this)) {
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, ProfileActivity::class.java)
                )
            }else{
                Utility.startActivityWithLeftToRightAnimation(
                    this,
                    Intent(this, LoginActivity::class.java)
                )
            }

        })
    }

    private fun callContactUs(mType:String) {
        mViewModel.initContactUs(true)!!.observe(this, Observer {
            closeDrawer()
            if (it!=null){
                if (it.status){
                    if (mType=="fb"){
                        val urlPage = ""+it.data[0].facebook
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)))
                        } catch (e: Exception) {
                            //Open url web page.
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)))
                        }
                    }else if (mType=="insta"){
                        val urlPage = ""+it.data[0].instagram
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)))
                        } catch (e: Exception) {
                            //Open url web page.
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)))
                        }
                    }else if(mType=="wp"){
                        whatsApp(""+it.data[0].mobile)
                    }else if(mType=="call"){
                        callTo(""+it.data[0].mobile)
                    }
                }

            }

        })


    }

    private fun callTo(mobile: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:"+mobile)
        startActivity(intent)
    }

    private fun whatsApp(mobile: String) {

        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, "")
//            if (mData.mobileCountry.length > 0)
//                sendIntent.putExtra(
//                    "jid", "${mData.mobileCountry}${mobile}@s.whatsapp.net"
//                )
//            else
                sendIntent.putExtra("jid", "${mobile.replace("-","").replace("+","")}@s.whatsapp.net")
            sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
        } catch (e: Exception) {
        }

    }

    private fun getAllMyCart() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllMyCart().observe(this@HomeActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    if (it.size > 0) {
                        flagCart=true
                        txtCartCount.text = "" + it.size
                    }
                }else {
                    flagCart=false
                    txtCartCount.text = "0"
                }
            })
        }
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
    }

    var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        openFragment(HomeFragment.newInstance())
                        return true
                    }
                    R.id.navigation_order -> {

                        if (Prefences.getIsLogin(this@HomeActivity)) {
                            Utility.startActivityWithLeftToRightAnimation(
                                this@HomeActivity,
                                Intent(this@HomeActivity, MyOrderListActivity::class.java)
                            )
                        }else{
                            Utility.startActivityWithLeftToRightAnimation(
                                this@HomeActivity,
                                Intent(this@HomeActivity, LoginActivity::class.java)
                            )
                        }

                        return true
                    }
                    R.id.navigation_offer -> {

                        Utility.startActivityWithLeftToRightAnimation(
                            this@HomeActivity,
                            Intent(this@HomeActivity,OffersActivity::class.java)
                        )

                        return true
                    }

                }
                return false


            }
        }

    fun openFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
//        transaction.addToBackStack("test")
        transaction.commit()
    }

    override fun onRestart() {
        super.onRestart()
        getAllMyCart()
    }

    override fun onResume() {
        super.onResume()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.txtNavUserName) as TextView
        val txtNavUserMobile = headerView.findViewById<View>(R.id.txtNavUserMobile) as TextView
        val imgNavProfilePic = headerView.findViewById<View>(R.id.imgNavProfilePic) as CircleImageView

        if(Prefences.getIsLogin(this)) {
            var urlimage=Prefences.getUserImage(this@HomeActivity)
            if (urlimage!!.endsWith(".jpg") || urlimage!!.endsWith("jpeg ")
                || urlimage!!.endsWith("png")){
                Glide.with(this@HomeActivity).load(urlimage).into(imgNavProfilePic)
            }else Glide.with(this@HomeActivity).load(R.drawable.ic_profile).into(imgNavProfilePic)

            navUsername.text = "" + Prefences.getFirstName(this@HomeActivity)
            txtNavUserMobile.text = "+91-" + Prefences.getUserMobile(this@HomeActivity)
        }else{
            navUsername.text = "Guest"
            txtNavUserMobile.text = "+91 ---------"
            Glide.with(this@HomeActivity).load(R.drawable.ic_profile).into(imgNavProfilePic)
        }
    }

    fun shareText(subject: String?, body: String?) {
        try {
            val txtIntent = Intent(Intent.ACTION_SEND)
            txtIntent.type = "text/plain"
            txtIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            txtIntent.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(txtIntent, "Share"))
        } catch (e: Exception) {
        }
    }


}