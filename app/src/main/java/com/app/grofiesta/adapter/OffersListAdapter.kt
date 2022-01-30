package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_offers.view.*


@SuppressLint("SetTextI18n")
class OffersListAdapter(
    var mList: List<ApiResponseModels.OffersResponse.Success>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<OffersListAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_offers, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: OffersListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.OffersResponse.Success) {

            mListItem.apply {
                itemView.apply {
                    Glide.with(itemView.context).load(urlimage).into(imgView!!)
                    txtOfferName.text=""+name
                }

                itemView.lytMain.setOnClickListener {
                    itemClick(absoluteAdapterPosition)
                }

            }
        }
    }


    override fun getItemCount(): Int {
        if (mList==null)
            return 0
        return mList.size
    }

//    fun setOnClickListener(onClickListener: onClickView) {
//        this.onClick = onClickListener
//    }

    interface onClickView {
        fun onClickView(position: Int, type: String)
    }
}