package com.app.grofiesta.ui.main.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.data.model.request.UserProfileRequest
import com.app.grofiesta.databinding.ActivityProfileBinding
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.login.LoginViewModel
import com.app.grofiesta.ui.main.view.product.ImagePreviewActivity
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_servcie.*
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.app_header_layout.imgBack
import kotlinx.android.synthetic.main.app_header_layout.txtPageTitle
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding

    val mContext by lazy { this@ProfileActivity }

    lateinit var mViewModel: LoginViewModel
    var FileImageSingle: MultipartBody.Part? = null
    var selectedImage1: Uri? = null
    var imageedit : RequestBody? = null
    var img=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)
        mViewModel.init(this)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Profile"
        binding.editMobile.setText(Prefences.getUserMobile(this))

        binding.imgCapture.setOnClickListener {
            callCamera()
        }

        binding.profileImage.setOnClickListener {
            if (img.endsWith(".jpg") || img.endsWith("jpeg ")
                || img.endsWith("png")){
                Intent(this@ProfileActivity, ImagePreviewActivity::class.java).apply {
                    putExtra("image", img)
                }.let {
                    Utility.startActivityWithLeftToRightAnimationContext(this@ProfileActivity!!, it)
                }

            }
        }
        callUserData()
    }

    private fun callUserData() {
        mViewModel.callUserDetail(Prefences.getUserId(this@ProfileActivity)!!, true)!!
            .observe(this, Observer {
                if (it.success != null) {

                    it.success.apply {
                        binding.editName.setText(firstname)
                        binding.editMobile.setText(telephone)
                        binding.editEmail.setText(email)
                        img=image
                        if (urlimage.endsWith(".jpg") || urlimage.endsWith("jpeg ")
                            || urlimage.endsWith("png")){
                            Glide.with(this@ProfileActivity).load(urlimage).into(binding.profileImage)
                        }

                        Prefences.setFirstName(this@ProfileActivity,firstname)
                        Prefences.setLastName(this@ProfileActivity,lastname)
                        Prefences.setUserMobile(this@ProfileActivity,telephone)
                        Prefences.setUserEmailId(this@ProfileActivity,email)
                        Prefences.setUserImage(this@ProfileActivity,""+urlimage)
                        Prefences.setIsDeliveryBoy(this@ProfileActivity,""+delevery_boy_status)

                    }


                } else Utility.showToast(mContext)
            })

    }

    fun clickSave(view: View) {
        callSaveProfileData()
    }

    private fun callSaveProfileData() {

        val firstname = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + binding.editName.text.toString().trim()
        )

        val editEmail = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + binding.editEmail.text.toString().trim()
        )
        val userId = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + Prefences.getUserId(this@ProfileActivity)
        )

        val mobile = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + binding.editMobile.text.toString().trim()
        )

        val address = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + Prefences.getAddress(this@ProfileActivity)
        )

        if (selectedImage1!=null) {
            val file1 = File(Utility.getRealPathFromURI(selectedImage1!!, this))
            val requestFile1 = RequestBody.create("multipart/form-data".toMediaType(), file1)
            FileImageSingle =
                MultipartBody.Part!!.createFormData("image", file1.name!!, requestFile1!!)
        }else{

        }

        imageedit = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + img
        )

        mViewModel.callSaveUserDetail(
            firstname,
            mobile,
            editEmail,
            userId,
            address,
            imageedit!!,
            FileImageSingle,
            true
        )!!.observe(this, Observer {
            if (it.success == 1) {
                showToast("Profile update successfully.")
                callUserData()

            } else Utility.showToast(mContext)
        })

    }

    private fun callCamera() {
        ImagePicker.Companion.with(this)
//            .crop() //Crop image(Optional), Check Customization for more option
            .compress(512) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            ) //Final image resolution will be less than 1080 x 1080(Optional)
            .start()

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            selectedImage1 = data!!.data

            binding.profileImage.setImageURI(selectedImage1)
        }
    }


}