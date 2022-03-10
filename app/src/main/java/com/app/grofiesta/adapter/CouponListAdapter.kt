package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_coupon.view.*
import kotlinx.android.synthetic.main.item_my_order_detail.view.*
import kotlinx.android.synthetic.main.item_my_order_detail.view.txtItemName


@SuppressLint("SetTextI18n")
class CouponListAdapter(
    var mList: List<ApiResponseModels.CouponListResponse.Data>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<CouponListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_coupon, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: CouponListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.CouponListResponse.Data) {

            itemView.apply {
                mListItem.apply {
                    txtItemName.text=""+coupon_name
                    txtDesc.text=""+coupon_desc

                }
            }

            itemView.lytMain.setOnClickListener { itemClick(adapterPosition) }
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

}