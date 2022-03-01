package com.app.grofiesta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.utils.Prefences
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlinx.android.synthetic.main.item_product_list.view.lytMain
import kotlinx.android.synthetic.main.item_product_list.view.productImg
import kotlinx.android.synthetic.main.item_product_list.view.txtDisplayPrice
import kotlinx.android.synthetic.main.item_product_list.view.txtMainPrice
import kotlinx.android.synthetic.main.item_product_list.view.txtName
import kotlinx.android.synthetic.main.item_product_list.view.txtWeightSize

import com.app.grofiesta.R
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.home_product_item.view.*
import kotlinx.android.synthetic.main.item_product_list.view.imgWishlist
import kotlinx.android.synthetic.main.item_product_list.view.lytAddtoCart
import kotlinx.android.synthetic.main.item_product_list.view.txtDiscount
import kotlinx.android.synthetic.main.item_product_list.view.txtLabel
import kotlinx.android.synthetic.main.item_product_list.view.txtOutOfStock


class ProductListAdapter(
    val mList: List<ApiResponseModels.ProductListingResponse.Data>,
    var itemClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<ProductListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_list, parent, false)

        return MyHolder(v)
    }


    override fun getItemCount(): Int {
        if (mList == null)
            return 0
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(mList: ApiResponseModels.ProductListingResponse.Data) {

            itemView.apply {
                mList.apply {
                    Glide.with(itemView.context).load(urlimage).into(productImg)
                    txtName.text = "" + product_name
                    txtWeightSize.text = "" + weight_size
                    txtDisplayPrice.text = "₹" + display_price
                    txtMainPrice.text = "₹" + main_price

                    if (discount_percent != null && discount_percent != "0" && discount_percent!="") {
                        txtDiscount.visibility = View.VISIBLE
                        txtDiscount.text = "" + discount_percent + "% Off"
                    } else
                        txtDiscount.visibility = View.GONE

                    if (qty=="" || qty =="0") {
                        lytAddtoCart.alpha = 0.7f
                        lytAddtoCart.isEnabled = false
                        txtOutOfStock.visibility = View.VISIBLE
                    } else {
                        lytAddtoCart.alpha = 1f
                        lytAddtoCart.isEnabled = true
                        txtOutOfStock.visibility = View.GONE
                    }


                }

            }

            itemView.lytMain.setOnClickListener { itemClick(adapterPosition, "Detail") }

            itemView.imgWishlist.setOnClickListener {
                if (Prefences.getIsLogin(itemView.context)) {
                    itemClick(adapterPosition, "Wishlist")
                    itemView.imgWishlist.setImageResource(R.drawable.ic_like_heart)
                }
                else
                    Utility.showToastForLogin(itemView.context)
            }

            itemView.lytAddtoCart.setOnClickListener {
                if(Prefences.getIsLogin(itemView.context)) {
                    if (itemView.txtLabel.text == "Add to Cart") {
                        itemView.txtLabel.text = "Go to Cart"
                        itemClick(adapterPosition, "Add")
                    } else {
                        itemClick(adapterPosition, "GoTOCart")
                    }
                }else Utility.showToastForLogin(itemView.context)

            }


        }
    }

}