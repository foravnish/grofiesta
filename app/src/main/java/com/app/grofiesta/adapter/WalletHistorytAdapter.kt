package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import kotlinx.android.synthetic.main.item_my_history.view.*
import kotlinx.android.synthetic.main.item_my_order.view.*


@SuppressLint("SetTextI18n")
class WalletHistorytAdapter(
    var mList: List<ApiResponseModels.MyWallet.Data.History>,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<WalletHistorytAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_history, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: WalletHistorytAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.MyWallet.Data.History) {

            itemView.apply {
                mListItem.apply {
                    txtCreditBal.text=""+mListItem.credit
                    txtDebitBal.text=""+mListItem.debit

                }
            }
//            itemView.txtDetail.setOnClickListener {
//                itemClick(adapterPosition)
//            }

        }
    }



    override fun getItemCount(): Int {
        return mList.size
    }

}