package com.app.grofiesta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.utils.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_item.view.*

class BannerGroFiestaPagerAdapter(
    context: Context,
    val images: List<ApiResponseModels.GroFiestaPageResponse.Success.Slider>,
    var itemClick: (Int) -> Unit) : PagerAdapter() {

    var context: Context
    var imageView : ImageView? = null

    init {
        this.context =context
    }

    override fun getCount(): Int {
        return  images!!.size
    }

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view === (`p1` as RelativeLayout)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container?.context)
            .inflate(R.layout.banner_item,container,false)

        imageView = itemView.findViewById(R.id.imageView) as ImageView

        Glide.with(itemView.context).load(images.get(position).urlimage).into(imageView!!)

        imageView!!.setOnClickListener { itemClick(position) }

        container.addView(itemView)

        return itemView
    }


}