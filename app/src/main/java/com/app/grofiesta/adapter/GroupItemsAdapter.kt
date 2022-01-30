package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.main.view.product.ProductDropDownListing
import kotlinx.android.synthetic.main.item_subproduct_new.view.*


@SuppressLint("SetTextI18n")
class GroupItemsAdapter(
    var activity: ProductDropDownListing,
    var mList: ArrayList<ApiResponseModels.DropDownResponse.Success.Children>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<GroupItemsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_subproduct_new, parent, false)
        val holder = MyHolder(v)
        holder.setIsRecyclable(false)
        return holder
    }

    override fun onBindViewHolder(holder: GroupItemsAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

//    fun addList(mUpdateList: ArrayList<ApiResponseModels.GroupItemsResponse.Response.ObjItemGroup.ObjItemLst>) {
//        mList.clear()
//        mList.addAll(mUpdateList)
//        notifyDataSetChanged()
//    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(
            mListItem: ApiResponseModels.DropDownResponse.Success.Children
        ) {

            itemView.txtProductName.text = mListItem.name

            itemView.lytMain.setOnClickListener { itemClick(adapterPosition) }
        }
    }


    fun getAmount(price: Double, qty: Long): String {
        return (price * qty).toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}