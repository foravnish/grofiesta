package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_search.view.*


@SuppressLint("SetTextI18n")
class SearchListAdapter(
    var mList: List<ApiResponseModels.ProductListingResponse.Data>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<SearchListAdapter.MyHolder>() {
    lateinit var onClick: onClickView

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.ProductListingResponse.Data) {

            itemView.apply {
                mListItem.apply {
                    txtItemName.text=""+mListItem.product_name
                    txtxSize.text="Size: "+mListItem.weight_size
                    txtPrice.text="₹"+mListItem.display_price
                }
            }
            itemView.lytMain.setOnClickListener {
                itemClick(adapterPosition)
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