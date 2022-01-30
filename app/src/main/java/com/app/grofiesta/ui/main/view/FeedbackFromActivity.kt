package com.app.grofiesta.ui.main.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.DocImagesListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.ui.main.view.product.ImagePreviewActivity
import com.app.grofiesta.ui.main.view.product.ProductViewModel
import com.app.grofiesta.ui.main.view.product.ServiceActivity
import com.app.grofiesta.utils.Utility
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_feedback_from.*
import kotlinx.android.synthetic.main.activity_feedback_from.editRemark
import kotlinx.android.synthetic.main.app_header_layout.*
import kotlinx.android.synthetic.main.app_header_layout.imgBack
import kotlinx.android.synthetic.main.app_header_layout.txtPageTitle
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FeedbackFromActivity : BaseActivity() {
    val mContext by lazy { this@FeedbackFromActivity }
    lateinit var mViewModel: ProductViewModel
    val mImageUrl = ArrayList<String>()
    var FileImageSingle: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_from)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@FeedbackFromActivity)

        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Feedback"

        editName.setText(""+Prefences.getFirstName(this)+" "+Prefences.getLastName(this))

        setList()

    }

    fun goToSubmit(v:View){

        if (editRemark.text.toString().isEmpty()){
            showToast("please enter Remark.")
            return
        }
        val customer_id = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + Prefences.getUserId(mContext)
        )
        val remark = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + editRemark.text.toString()
        )


        val FileImage: MutableList<MultipartBody.Part> = ArrayList()
//        descriptionList.add(createFormData.createFormData("param_name_here", values))

        if (mImageUrl.size > 0) {
            for(i in 0..mImageUrl.size-1){
                var  myUri = Uri.parse(mImageUrl[i])
                val file1 = File(Utility.getRealPathFromURI(myUri!!,this))
                val requestFile1 = RequestBody.create("multipart/form-data".toMediaType(), file1)
                FileImageSingle = MultipartBody.Part!!.createFormData("image[]", file1.name!!, requestFile1!!)
                FileImage.add(FileImageSingle!!)

            }
        }

        mViewModel.initSendFeedback(customer_id, remark, FileImage!!,true)!!.observe(this, Observer {
            if (it.status != null) {
                showToast("Feedback Successfully sent!")

                Intent(this@FeedbackFromActivity, FeedbackFromActivity::class.java).apply {
                }.let {
                    Utility.startActivityWithLeftToRightAnimation(this,it)
                }
                finish()


            } else Utility.showToast(mContext)
        })

    }

    private fun setList() {
        rvGalleryDocuments.layoutManager = GridLayoutManager(this, 5)
        val mAdapter = DocImagesListAdapter(getListMandatory())
        mAdapter.setOnClickListener(object : DocImagesListAdapter.onClickView {
            override fun onClickView(position: Int, type: String) {
                if (type.equals("Add", true)) {
                    callCamera()
                } else {
                    val mListImg = ArrayList<String>()
                    mListImg.add(getListMandatory()[position].imgUrl)
                    val intent =
                        Intent(this@FeedbackFromActivity, ImagePreviewActivity::class.java)
                    intent.putExtra("image", mListImg[position])
                    intent.putExtra("mType", "0")
                    startActivity(intent)
                }
            }

        })
        rvGalleryDocuments.adapter = mAdapter
    }

    private fun getListMandatory(): ArrayList<ApiResponseModels.ImageDoc> {
        val mList = ArrayList<ApiResponseModels.ImageDoc>()
        if (mImageUrl.size > 0) {
            for (item in mImageUrl) {
                mList.add(ApiResponseModels.ImageDoc(item, "Img"))
            }
        }
        mList.add(ApiResponseModels.ImageDoc("", "Add"))
        return mList
    }

    fun clickPickPhoto(view: View) {
        callCamera()
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

            var selectedImage1 = data!!.data

            mImageUrl.add(selectedImage1.toString())
            setList()

        }
    }


}