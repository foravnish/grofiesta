package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R


@SuppressLint("SetTextI18n")
class MyOrderDetailAdapter(
//    var mList: List<ApiResponseModels.AddToCart>,
//    var activity: MyCartActivity,
//    var divSr:String,
    var itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyOrderDetailAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_order_detail, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyOrderDetailAdapter.MyHolder, p1: Int) {
        holder.bindData(/*mList[p1]*/)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(/*mListItem: ApiResponseModels.AddToCart*/) {
//
//            if (mListItem.sizeName != null && mListItem.sizeName != "") {
//                itemView.txtSize.visibility = View.VISIBLE
//                itemView.txtSize.text = "Size ${mListItem.sizeName}"
//            } else {
//                itemView.txtSize.text = ""
//                itemView.txtSize.visibility = View.GONE
//            }
//
//            if (mListItem.type.equals("Online", true)) {
//                itemView.imgDelete.visibility = View.GONE
//            } else {
//                itemView.imgDelete.visibility = View.VISIBLE
//            }
//
//            itemView.imgIncrease.setOnClickListener {
//                var mQty = itemView.txtValue.text.toString().toInt()
//                mQty++
//
//                val PriceNew: String? = UtilityGetLocalDB.getProductItemDataForQTY(
//                    "" + mListItem.itemTbSr,
//                    "" + mQty,divSr,
//                    activity
//                )
//                if (PriceNew == "") {
//                    itemView.txtPrice.text = "₹ ${Utils.removeDot(mListItem.price)}/-"
//                    activity.updateItem(mListItem, mQty)
//                } else {
//                    mListItem.price = "" + PriceNew
//                    itemView.txtPrice.text = "₹ ${Utils.removeDot("" + PriceNew)}/-"
//                    activity.updateItem(mListItem, mQty)
//                }
//
////                activity.updateItem(mListItem, mQty)
//            }
//
//
//            try {
//                val mDataPriceLst: ArrayList<ApiResponseModels.ProductPriceLst> =
//                    UtilityGetLocalDB.getProductItemDataForPricing(
//                        "" + mListItem.itemTbSr!!,
//                        activity
//                    )
//                if (mDataPriceLst.size < 2) {
//                    itemView.txtPricing.setVisibility(View.GONE)
//                } else {
//                    itemView.txtPricing.setVisibility(View.VISIBLE)
//                }
//
//
//                itemView.txtPricing.setOnClickListener {
//                    val mDataPriceLst: java.util.ArrayList<ApiResponseModels.ProductPriceLst> =
//                        UtilityGetLocalDB.getProductItemDataForPricing(
//                            "" + mListItem.itemTbSr,
//                            activity
//                        )
//
//                    Log.d("DataHere", "" + Gson().toJson(mDataPriceLst))
//                    val dialog: Dialog =
//                        Utils.MyCustomDialogForPriceList(activity, R.layout.price_listing)
//                    val recyclerList: RecyclerView = dialog.findViewById(R.id.recycler_list)
//                    val linearLayoutManager =
//                        LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//                    recyclerList.layoutManager = linearLayoutManager
//                    adapter = PriceListAdapter(mDataPriceLst)
//                    recyclerList.adapter = adapter
//                    dialog.show()
//
//                }
//
//                activity.updateItem(mListItem, itemView.txtValue.text.toString().toInt())
//
//            } catch (e: Exception) {
//                itemView.txtPricing.setVisibility(View.GONE)
//                Log.d("EROORHere", "" + e)
//            }
//
////            itemView.txtValue.setOnClickListener {
////                crtQuantityDialog(
////                    itemView.context,
////                    mListItem,
////                    itemView.txtValue.text.toString().trim()
////                )
////            }
//
//            val lenth = itemView.txtValue.text.length.toLong()
//            if (lenth <= 7) {
//                val layoutParams =
//                    itemView.txtValue.layoutParams as ViewGroup.MarginLayoutParams
//                layoutParams.height = Utils.dpToPx(itemView.context, 42)
//                layoutParams.width = Utils.dpToPx(itemView.context, 50)
//                itemView.txtValue.requestLayout()
//            }
//
//            itemView.imgDecrease.setOnClickListener {
//                if (mListItem.type.equals("Online", true)) {
//                    var mQty = itemView.txtValue.text.toString().toInt()
//                    if (mQty > 0) {
//                        mQty--
////                        activity.updateItem(mListItem, mQty)
//
//                        val PriceNew: String? = UtilityGetLocalDB.getProductItemDataForQTY(
//                            "" + mListItem.itemTbSr,
//                            "" + mQty,divSr,
//                            activity
//                        )
//                        if (PriceNew == "") {
//                            itemView.txtPrice.text = "₹ ${Utils.removeDot(mListItem.price)}/-"
//                            activity.updateItem(mListItem, mQty)
//                        } else {
//                            mListItem.price = "" + PriceNew
//                            itemView.txtPrice.text = "₹ ${Utils.removeDot("" + PriceNew)}/-"
//                            activity.updateItem(mListItem, mQty)
//                        }
//
//                    } /*else
//                        activity.deleteValueFromDb(mListItem.ProductId)*/
//                } else {
//                    var mQty = itemView.txtValue.text.toString().toInt()
//                    if (mQty >Math.round(mListItem.minQty.toDouble()).toInt()) {
////                    if (mQty > 1) {
//                        mQty--
////                        activity.updateItem(mListItem, mQty)
//
//                        val PriceNew: String? = UtilityGetLocalDB.getProductItemDataForQTY(
//                            "" + mListItem.itemTbSr,
//                            "" + mQty,divSr,
//                            activity
//                        )
//                        if (PriceNew == "") {
//                            itemView.txtPrice.text = "₹ ${Utils.removeDot(mListItem.price)}/-"
//                            activity.updateItem(mListItem, mQty)
//                        } else {
//                            mListItem.price = "" + PriceNew
//                            itemView.txtPrice.text = "₹ ${Utils.removeDot("" + PriceNew)}/-"
//                            activity.updateItem(mListItem, mQty)
//                        }
//                    } else
////                        activity.deleteValueFromDb(mListItem.ProductId)
//                        activity.getDeleteItem(mListItem.ProductId)
//                }
//            }
//
//            itemView.imgDelete.setOnClickListener {
//                showAlertPopup(itemView.context, mListItem.ProductId)
//            }
//
//
//
//            itemView.lytChangeDate.setOnClickListener {
//                val c = Calendar.getInstance()
//                val year = c.get(Calendar.YEAR)
//                val month = c.get(Calendar.MONTH)
//                val day = c.get(Calendar.DAY_OF_MONTH)
//                val dpd = DatePickerDialog(
//                    itemView.context,
//                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                        c.set(Calendar.YEAR, year)
//                        c.set(Calendar.MONTH, monthOfYear)
//                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                        itemView.txtstartday.text = Utils.convertDateFormat(
//                            "dd-MM-yyyy",
//                            "dd MMM yyyy",
//                            Utils.date_in_yyyy_MM_dd(c.time)
//                        )
//                        /* activity.updateDateInCart(
//                             mListItem, Utils.convertDateFormat(
//                                 "dd-MM-yyyy",
//                                 "dd-MMM-yyyy",
//                                 Utils.date_in_yyyy_MM_dd(c.time)
//                             )!!
//                         )*/
//                        activity.updateDate(
//                            mListItem.itemTbSr, mListItem.startdate, Utils.convertDateFormat(
//                                "dd-MM-yyyy",
//                                "dd-MMM-yyyy",
//                                Utils.date_in_yyyy_MM_dd(c.time)
//                            )!!
//                        )
//
//                    },
//                    year, month, day
//                )
//                dpd.datePicker.minDate = c.timeInMillis + 24 * 60 * 60 * 1000
//                dpd.show()
//            }


        }
    }


    override fun getItemCount(): Int {
        return 3
    }

}