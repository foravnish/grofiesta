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
    var mList: List<ApiResponseModels.OrderLIstingNewResponse.Data>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int,String) -> Unit
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
        fun bindData(mListItem: ApiResponseModels.OrderLIstingNewResponse.Data) {

            itemView.apply {
                mListItem.apply {
                    txtOrderNumber.text="Order Id: #"+order_id
                    txtItemName.text="Address: "+address
                    txtOrderdate.text="Order Date: "+date_added
                    txtTotalPrice.text="Total Price: ₹"+total

                    if (status=="1") {
                        txtStatus.setTextColor(context.getColor(R.color.orange))
                        txtStatus.text = "Order Confirm"
                        txtCancel.visibility=View.VISIBLE
                    } else if (status=="2") {
                        txtStatus.setTextColor(context.getColor(R.color.item_color))
                        txtStatus.text = "Under Process"
                        txtCancel.visibility=View.VISIBLE
                    } else if (status=="3") {
                        txtStatus.setTextColor(context.getColor(R.color.item_color))
                        txtStatus.text = "Dispatch"
                        txtCancel.visibility=View.GONE
                    }else if (status=="4") {
                        txtStatus.setTextColor(context.getColor(R.color.green))
                        txtStatus.text = "Delivered"
                        txtCancel.visibility=View.GONE
                    }else if (status=="5") {
                        txtStatus.setTextColor(context.getColor(R.color.red))
                        txtStatus.text = "Cancelled"
                        txtCancel.visibility=View.GONE
                    }

                }
            }
            itemView.txtDetail.setOnClickListener {
                itemClick(adapterPosition,"Detail")
            }
            itemView.txtCancel.setOnClickListener {
                itemClick(adapterPosition,"Cancel")
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