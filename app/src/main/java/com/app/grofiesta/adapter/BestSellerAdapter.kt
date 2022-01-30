package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_item.view.*

class BestSellerAdapter(
    val mList: ArrayList<ApiResponseModels.BestSellerResponse.Success>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<BestSellerAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_product_item, parent, false)

        return MyHolder(v)
    }

    override fun getItemCount(): Int {
        if (mList==null)
            return 0
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(mList: ApiResponseModels.BestSellerResponse.Success) {

                itemView.apply {
                    mList.apply {
                        lytPrice.visibility=View.GONE
                        Glide.with(itemView.context).load(urlimage).into(productImg)
                        txtName.text=""+category_name
                        txtWeightSize.text=""+gfid
                    }

                }

            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }

        }
    }


}