package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_my_delivery.view.*
import kotlinx.android.synthetic.main.item_my_order.view.*
import kotlinx.android.synthetic.main.item_my_order.view.txtDetail
import kotlinx.android.synthetic.main.item_my_order.view.txtOrderNumber


@SuppressLint("SetTextI18n")
class MyDeliveryListAdapter(
    var mList: List<ApiResponseModels.MyDeliveryResponse.Data>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int,String) -> Unit
) :
    RecyclerView.Adapter<MyDeliveryListAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_delivery, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyDeliveryListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.MyDeliveryResponse.Data) {

            itemView.apply {
                mListItem.apply {
                    txtOrderNumber.text="Item Id : #"+order_id
                    txtCustomer_name.text="Name: "+customer_name
                    txtMobile.text="Mobile : "+customer_mobile
                    txtAddress.text="Address: "+address

                }
            }
            itemView.txtChageStatus.setOnClickListener {
                itemClick(adapterPosition,"ChageStatus")
            }
            itemView.txtDetail.setOnClickListener {
                itemClick(adapterPosition,"Detail")
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