package com.app.grofiesta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ananda.retailer.Views.Activities.Grocery.viewmodel.GroceryViewModel
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.utils.Utility
import kotlinx.android.synthetic.main.dymanic_home_product_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class HomePageDynamicAdapter(
    val mList: List<ApiResponseModels.DymainHomeProductResponse.Data>,
    val lifecycleScope: LifecycleCoroutineScope,
    val  viewModel: GroceryViewModel,
    val requireActivity: FragmentActivity,
    var itemClick: (ApiResponseModels.DymainHomeProductResponse.Data.Productsdata,String,String) -> Unit
) :
    RecyclerView.Adapter<HomePageDynamicAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var v: View
        v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dymanic_home_product_item, parent, false)

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


        fun bindData(mList: ApiResponseModels.DymainHomeProductResponse.Data) {

            itemView.apply {
                mList.apply {

                    txtCatName.text = "" + mList.category_name

                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.IO) {
                            withContext(lifecycleScope.coroutineContext) {


                                for (i in 0..mList.productsdata.size - 1) {
                                    lifecycleScope.launchWhenStarted {
                                        viewModel.getMyCart(mList.productsdata[i].product_id)
                                            .observe(requireActivity, Observer {
                                                if (it != null && it.isNotEmpty()) {
                                                    mList.productsdata[i].hasCart = true
                                                }
                                            })

                                    }
                                }

                            }
                        }

                        if (mList.productsdata.size > 0)
                            initAdapter(context,itemView,mList.productsdata)

                    }



                }

            }


        }
    }

    private fun initAdapter(
        context: Context,
        itemView: View,
        mData: List<ApiResponseModels.DymainHomeProductResponse.Data.Productsdata>
    ) {
        var horizontalLayout = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        itemView.rvDynamicProductHorizontal.layoutManager = horizontalLayout

        val mAdapter = HomeItemsAdapter("1", Utility.convertModel(mData)) { pos, type ->
            when (type) {
                "Detail" ->openDetailPage(mData[pos],mData[pos].product_id,"Detail")
                "Add" -> addToCart(mData[pos],"Add")
                "GoTOCart" -> openMyCartScreen(mData[pos],"GoTOCart")
            }

        }
        itemView.rvDynamicProductHorizontal.adapter = mAdapter
    }

    private fun openMyCartScreen(mData: ApiResponseModels.DymainHomeProductResponse.Data.Productsdata,mType:String) {

        itemClick(mData,"",mType)
    }

    private fun addToCart(mData: ApiResponseModels.DymainHomeProductResponse.Data.Productsdata,mType:String) {

        itemClick(mData,"",mType)
    }

    private fun openDetailPage(mData: ApiResponseModels.DymainHomeProductResponse.Data.Productsdata,product_id:String,mType:String) {
        itemClick(mData,product_id,mType)
    }



}