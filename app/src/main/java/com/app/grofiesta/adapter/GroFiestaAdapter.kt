package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_item.view.*

class GroFiestaAdapter(
    val mList: List<ApiResponseModels.GroFiestaPageResponse.Success.Image>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<GroFiestaAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_gro_fista_page_item, parent, false)

        return MyHolder(v)
    }

//    fun addList(mUpdateList: ArrayList<ApiResponseModels.Response.UserList>) {
//        mList.clear()
//        mList.addAll(mUpdateList)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        if (mList==null)
            return 0
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(mList: ApiResponseModels.GroFiestaPageResponse.Success.Image) {

                itemView.apply {
                    mList.apply {
                        Glide.with(itemView.context).load(urlimage).into(productImg)
                        txtName.text=""+name

                    }

                }

            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }

        }
    }


}