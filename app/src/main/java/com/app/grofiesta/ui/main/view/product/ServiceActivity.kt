package com.app.grofiesta.ui.main.view.product

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.adapter.DocImagesListAdapter
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.Utility
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_drop_down_listing.*
import kotlinx.android.synthetic.main.activity_product_drop_down_listing.imgBack
import kotlinx.android.synthetic.main.activity_product_drop_down_listing.txtPageTitle
import kotlinx.android.synthetic.main.activity_servcie.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File





class ServiceActivity : BaseActivity() {
    val mContext by lazy { this@ServiceActivity }

    lateinit var mViewModel: ProductViewModel
    val mImageUrl = ArrayList<String>()
    var FileImageSingle: MultipartBody.Part? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servcie)

        mViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        mViewModel.init(this@ServiceActivity)


        imgBack.setOnClickListener { finish() }
        txtPageTitle.text = "Services"

        setList()

        callServies()
    }

    fun goToSubmit(v:View){

        if (editRemark.text.toString().isEmpty()){
            showToast("please enter a message.")
            return
        }
        val customer_id = RequestBody.create(
            "multipart/form-data".toMediaType(),
            "" + Prefences.getUserId(mContext)
        )
        val description = RequestBody.create(
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

        mViewModel.initAddSeries(customer_id, description, FileImage!!,true)!!.observe(this, Observer {
            if (it.status != null) {
                showToast("Successfully sent!")

                Intent(this@ServiceActivity, ServiceActivity::class.java).apply {
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
                        Intent(this@ServiceActivity, ImagePreviewActivity::class.java)
                    intent.putExtra("image", mListImg[position])
                    intent.putExtra("mType", "0")
                    startActivity(intent)
                }
            }

        })
        rvGalleryDocuments.adapter = mAdapter
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

    fun clickPickPhoto(view: View) {
        callCamera()
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

    private fun callServies() {

        mViewModel.initMyServices(true)!!.observe(this, Observer {
            if (it.success != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtWeb.setText(
                        Html.fromHtml(
                            "" + it.success.description,
                            Html.FROM_HTML_MODE_COMPACT
                        )
                    );
                } else {
                    txtWeb.setText(Html.fromHtml("" + it.success.description))
                }

            } else Utility.showToast(mContext)
        })


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