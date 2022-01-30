package com.app.grofiesta.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.main.view.product.ProductDropDownListing
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_place_product_raw.view.*

class ExpandableAdapter(
    var activity: ProductDropDownListing,
    val mList: ArrayList<ApiResponseModels.DropDownResponse.Success>,
    var itemClick: (String) -> Unit
) :
    RecyclerView.Adapter<ExpandableAdapter.MyHolder>() {

    var mIndex = -1
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place_product_raw, parent, false)
        val holder = MyHolder(v)
        holder.setIsRecyclable(false)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(mList: ApiResponseModels.DropDownResponse.Success) {
            if (mIndex == adapterPosition) {
                itemView.rvProductItems.visibility = View.VISIBLE
                itemView.cardMain.setCardBackgroundColor(itemView.resources.getColor(R.color.light_green2))
                Glide.with(itemView.context).load(R.drawable.ic_baseline_keyboard_arrow)
                    .into(itemView.imgDownUp)
            } else {
                Glide.with(itemView.context).load(R.drawable.ic_down_arrow).into(itemView.imgDownUp)
                itemView.rvProductItems.visibility = View.GONE
                itemView.cardMain.setCardBackgroundColor(itemView.resources.getColor(R.color.light_green2))
            }

            itemView.txtItemName.text = mList.category_name

            itemView.rvProductItems.setHasFixedSize(true)
            val mListNew = mList.children
            itemView.rvProductItems.layoutManager = LinearLayoutManager(itemView.context)
            val mAdapter = GroupItemsAdapter(activity,  mListNew) {
                itemClick(mListNew[it].sid)

            }
            itemView.rvProductItems.adapter = mAdapter

            itemView.cardMain.setOnClickListener {
                mIndex = if (mIndex == adapterPosition) {
                    -1
                } else
                    adapterPosition
                activity.mSelectedPos = mIndex
                notifyDataSetChanged()
            }
        }
    }

    fun openSelectedPosition() {
        mIndex = activity.mSelectedPos
        notifyDataSetChanged()
    }

}