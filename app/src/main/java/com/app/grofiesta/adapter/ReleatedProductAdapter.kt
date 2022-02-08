package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_product_item.view.*

class ReleatedProductAdapter(
    val mList: ArrayList<ApiResponseModels.RelatedProductResponse.Success>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ReleatedProductAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View

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


        fun bindData(mList: ApiResponseModels.RelatedProductResponse.Success) {

//            if (type == "1" ) {

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

                    }


                }

//            }
//            else if (type == "2")
//                itemView.productImg.setImageDrawable(
//                    itemView.context.getResources().getDrawable(R.drawable.images2)
//                );
//            else if (type == "3" || type  == "4")
//                itemView.productImg.setImageDrawable(
//                    itemView.context.getResources().getDrawable(R.drawable.images3)
//                )
//            else if (type == "5")
//                itemView.productImg.setImageDrawable(
//                    itemView.context.getResources().getDrawable(R.drawable.image4)
//                )

            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }

//            if (mIndex == adapterPosition) {
//                itemView.cardViewAccount.setCardBackgroundColor(itemView.resources.getColor(R.color.colorAccentLite11))
//                itemView.imgTick.visibility = View.VISIBLE
//            } else {
//                itemView.cardViewAccount.setCardBackgroundColor(itemView.resources.getColor(R.color.colorWhite))
//                itemView.imgTick.visibility = View.GONE
//            }
//
//            itemView.txtUserName.text = mList.name
//            itemView.txtGmail.text = mList.loginID
//            itemView.txtNickName.text = mList.name
//
//            if (mList.isWorking) {
//                if(mList.companyName.length>0)
//                    itemView.txtStatus.text="Working (${mList.companyName})"
//                else itemView.txtStatus.text="Working"
//                itemView.txtStatus.setTextColor(itemView.context.resources.getColor(R.color.secondary_text_default_material_light))
//            }else{
//                itemView.txtStatus.setTextColor(itemView.context.resources.getColor(R.color.colorRed))
//                itemView.txtStatus.text = "Expired"
//            }
//
//
//            itemView.cardViewAccount.setOnClickListener {
//                mIndex = adapterPosition
//                itemClick(adapterPosition)
//                notifyDataSetChanged()
//            }
        }
    }


}