package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_item.view.*

class HomeItemsAdapter(
    var type: String,
    val mList: ArrayList<ApiResponseModels.GroProductResponse.Success>,
    var itemClick: (Int,String) -> Unit
) :
    RecyclerView.Adapter<HomeItemsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        if (type=="4")
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_product_item2, parent, false)
        else
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_product_item, parent, false)

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


        fun bindData(mList: ApiResponseModels.GroProductResponse.Success) {

                itemView.apply {
                    mList.apply {
                        Glide.with(itemView.context).load(urlimage).into(productImg)
                        txtName.text=""+product_name
                        txtWeightSize.text=""+weight_size
                        txtDisplayPrice.text="₹"+display_price
                        txtMainPrice.text="₹"+main_price

                        if (discount_percent!=null && discount_percent!="0"){
                            txtDiscount.visibility=View.VISIBLE
                            txtDiscount.text=""+discount_percent+"% Off"
                        }else
                            txtDiscount.visibility=View.GONE


                        if (hasCart)
                            itemView.txtLabel.text="Go to Cart"
                        else
                            itemView.txtLabel.text="Add to Cart"
                    }

                }


            itemView.productImg.setOnClickListener {
                itemClick(adapterPosition,"Detail")
            }
            itemView.lytAddtoCart.setOnClickListener {
                if (itemView.txtLabel.text=="Add to Cart"){
                    itemView.txtLabel.text="Go to Cart"
                    itemClick(adapterPosition,"Add")
                }else{
                    itemClick(adapterPosition,"GoTOCart")
                }

            }

        }
    }


}