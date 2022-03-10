package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ananda.retailer.Room.Tables.MyCart
import com.app.grofiesta.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_cart_checkout.view.*


@SuppressLint("SetTextI18n")
class MyCartItemsListCheckoutAdapter(
    var mList: List<MyCart>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyCartItemsListCheckoutAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_cart_checkout, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyCartItemsListCheckoutAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: MyCart) {

            itemView.apply {
                mListItem.apply {
                    txtProductName.text=""+product_name
                    txtGST.text="GST: ₹"+gst
                    txtPrice.text="₹"+display_price
                    txtSize.text=""+weight_size
                    txtTotalAmt.text="Total : ₹"+totalAmount
                    txtQty.text=""+qty

                    Glide.with(itemView.context).load(image).error(R.drawable.place_holder).into(imgProduct!!)

                }
            }

        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

//    fun setOnClickListener(onClickListener: onClickView) {
//        this.onClick = onClickListener
//    }

    interface onClickView {
        fun onClickView(position: Int, type: String)
    }
}