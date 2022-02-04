package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_my_order_detail.view.*


@SuppressLint("SetTextI18n")
class MyOrderDetailDeliveryBoyAdapter(
    var mList: List<ApiResponseModels.MyDeliveryResponse.Data.OrderDetail>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyOrderDetailDeliveryBoyAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_order_detail, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyOrderDetailDeliveryBoyAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.MyDeliveryResponse.Data.OrderDetail) {

            itemView.apply {
                mListItem.apply {
                    txtOrderNumber.text="Order Id : #"+order_detail_id
                    txtItemName.text=""+item_name
                    txtPrice.text="₹"+price
                    txtQty.text="Qty: "+quantity

                }
            }

        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

}