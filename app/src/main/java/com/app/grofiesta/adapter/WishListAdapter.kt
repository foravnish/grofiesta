package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_wish_list.view.*

class WishListAdapter(
    val mList: List<ApiResponseModels.ProductListingResponse.Data>,
    var itemClick: (Int,String) -> Unit
) :
    RecyclerView.Adapter<WishListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wish_list, parent, false)

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
                }
            }

            itemView.lytMain.setOnClickListener {
                itemClick(adapterPosition,"detail")
            }

            itemView.imgClose.setOnClickListener {
                itemClick(adapterPosition,"close") }

        }
    }

}