package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_my_order.view.*


@SuppressLint("SetTextI18n")
class MyOrderListAdapter(
    var mList: List<ApiResponseModels.MyOderResponse.Success>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyOrderListAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_order, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyOrderListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.MyOderResponse.Success) {

            itemView.apply {
                mListItem.apply {
                    txtOrderNumber.text="Order Id : #"+od_id
                    txtItemName.text=""+item_name
                    txtOrderdate.text="Order Date : "+date_added
                    txtPrice.text="₹"+price
                    txtQty.text="Qty: "+quantity

                    if (status=="1") {
                        txtStatus.setTextColor(context.getColor(R.color.red))
                        txtStatus.text = "Pending"
                    } else if (status=="2") {
                        txtStatus.setTextColor(context.getColor(R.color.orange))
                        txtStatus.text = "Dispatch"
                    } else if (status=="3") {
                        txtStatus.setTextColor(context.getColor(R.color.green))
                        txtStatus.text = "Delivered"
                    }

                }
            }
            itemView.txtDetail.setOnClickListener {
               // itemClick(adapterPosition)
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