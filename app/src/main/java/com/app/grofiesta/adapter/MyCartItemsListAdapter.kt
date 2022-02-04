package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ananda.retailer.Room.Tables.MyCart
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_cart.view.*


@SuppressLint("SetTextI18n")
class MyCartItemsListAdapter(
    var mList: List<ApiResponseModels.ProductListingResponse.Data>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int, ApiResponseModels.ProductListingResponse.Data, String) -> Unit
) :
    RecyclerView.Adapter<MyCartItemsListAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_cart, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyCartItemsListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.ProductListingResponse.Data) {

            try {
                itemView.apply {
                    mListItem.apply {
                        txtProductName.text = "" + product_name
                        txtCatName.text = "" + category_id
                        txtPrice.text = "₹" + display_price
                        txtOffPrice.text = "₹" + main_price
                        txtSize.text = "" + weight_size
                        if (qwantity != null && qwantity != ""){
                            var mTotal = qwantity.toDouble() * display_price.toDouble()
                            txtTotalAmt.text = "Total : ₹" + mTotal
                        }

                        txtValue.text = "" + qwantity

                        Glide.with(itemView.context).load(urlimage).error(R.drawable.place_holder)
                            .into(imgProduct!!)

                        imgIncrease.setOnClickListener {
                            itemClick(adapterPosition, mListItem, "Plus")
                        }
                        imgDecrease.setOnClickListener {
                            itemClick(adapterPosition, mListItem, "Minus")
                        }
                        imgDelete.setOnClickListener {
                            itemClick(adapterPosition, mListItem, "Delete")
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }


    interface onClickView {
        fun onClickView(position: Int, type: String)
    }
}