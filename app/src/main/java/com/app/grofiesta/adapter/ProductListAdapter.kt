package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.home_product_item.view.*
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlinx.android.synthetic.main.item_product_list.view.lytMain
import kotlinx.android.synthetic.main.item_product_list.view.productImg
import kotlinx.android.synthetic.main.item_product_list.view.txtDisplayPrice
import kotlinx.android.synthetic.main.item_product_list.view.txtMainPrice
import kotlinx.android.synthetic.main.item_product_list.view.txtName
import kotlinx.android.synthetic.main.item_product_list.view.txtWeightSize

import com.app.grofiesta.R


class ProductListAdapter(
    val mList: List<ApiResponseModels.ProductListingResponse.Data>,
    var itemClick: (Int,String) -> Unit
) :
    RecyclerView.Adapter<ProductListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_list, parent, false)

        return MyHolder(v)
    }


    override fun getItemCount(): Int {
        if (mList == null)
            return 0
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(mList: ApiResponseModels.ProductListingResponse.Data) {

            itemView.apply {
                mList.apply {
                    Glide.with(itemView.context).load(urlimage).into(productImg)
                    txtName.text = "" + product_name
                    txtWeightSize.text = "" + weight_size
                    txtDisplayPrice.text = "₹" + display_price
                    txtMainPrice.text = "₹" + main_price

                    if (hasWishList)
                        imgWishlist.setImageResource(R.drawable.ic_like_heart)
                    else
                        imgWishlist.setImageResource(R.drawable.ic_like_heart_unfilled)


                }


            }

            itemView.lytMain.setOnClickListener { itemClick(adapterPosition,"Detail") }

            itemView.imgWishlist.setOnClickListener {  itemClick(adapterPosition,"Wishlist") }


        }
    }

}