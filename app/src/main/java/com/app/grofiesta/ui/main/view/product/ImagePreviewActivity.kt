package com.app.grofiesta.ui.main.view.product

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        imgBack.setOnClickListener { finish() }

        val mImage = intent.getStringExtra("image")
        if (intent.hasExtra("mType")){
            fullImage.setImageURI(Uri.parse(mImage))
        }else{

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()

            Glide.with(this)
                .load(mImage) // Uri of the picture
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(fullImage)


        }

    }
}
