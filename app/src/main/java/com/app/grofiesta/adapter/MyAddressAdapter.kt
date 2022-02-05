package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.accountapp.accounts.utils.Prefences
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_my_address.view.*


@SuppressLint("SetTextI18n")
class MyAddressAdapter(
    var mList: ArrayList<ApiResponseModels.MyAddressList.DataAddress>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<MyAddressAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_address, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyAddressAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.MyAddressList.DataAddress) {

            itemView.apply {
                mListItem.apply {
                    txtAddress.text = "" + address
                    txtPincpode.text = "Pincode #" + postcode

                    if (Prefences.getAddressId(context).equals(address_id)) {
                        txtSetAsDefault.text = "Default"
                        Prefences.setPincode(context, postcode)
                        Prefences.setAddress(context, address)
                        Prefences.setAddressId(context, address_id)
                    } else {
                        txtSetAsDefault.text = "Set As Default"
                    }
                }
            }
            itemView.txtEditAddress.setOnClickListener {
                itemClick(adapterPosition, "Edit")
            }
            itemView.txtDeleteAddress.setOnClickListener {
                if (Prefences.getAddressId(itemView.context).equals(mListItem.address_id))
                    Toast.makeText(itemView.context, "Default address can't delete.", Toast.LENGTH_SHORT)
                        .show()
                else
                    itemClick(adapterPosition, "Delete")

            }
            itemView.txtSetAsDefault.setOnClickListener {
                if (Prefences.getAddressId(itemView.context).equals(mListItem.address_id))
                    Toast.makeText(itemView.context, "Already set as Default", Toast.LENGTH_SHORT)
                        .show()
                else
                    itemClick(adapterPosition, "Default")
            }

        }

    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 0
        return mList.size
    }
}




