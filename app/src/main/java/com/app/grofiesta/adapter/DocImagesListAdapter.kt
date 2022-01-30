package com.app.grofiesta.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_servcie.*
import kotlinx.android.synthetic.main.item_docs_images_raw.view.*


@SuppressLint("SetTextI18n")
class DocImagesListAdapter(
    var mList: ArrayList<ApiResponseModels.ImageDoc>
) :
    RecyclerView.Adapter<DocImagesListAdapter.MyHolder>() {
    var mOldDate = ""
    lateinit var onClick: onClickView
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_docs_images_raw, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: DocImagesListAdapter.MyHolder, p1: Int) {
        holder.bindData(mList[p1])
    }

    fun addList(mObj: ArrayList<ApiResponseModels.ImageDoc>) {
        val size = this.mList.size
        this.mList.addAll(mObj)
        val sizeNew = this.mList.size
        notifyItemRangeChanged(size, sizeNew)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(mListItem: ApiResponseModels.ImageDoc) {

            if (mListItem.type.equals("Add", true)) {
                itemView.lytAdd.visibility = View.VISIBLE
                itemView.cardViewImage.visibility = View.GONE
            } else {
                var  myUri = Uri.parse(mListItem.imgUrl)

                itemView.imgPic.setImageURI(myUri)

//                Glide.with(itemView.context).load(mListItem.imgUrl)
//                    .placeholder(R.drawable.place_holder).into(itemView.imgPic)
                itemView.lytAdd.visibility = View.GONE
                itemView.cardViewImage.visibility = View.VISIBLE
            }

            itemView.cardViewImage.setOnClickListener {
                onClick.onClickView(adapterPosition, "View")
            }

            itemView.lytAdd.setOnClickListener {
                onClick.onClickView(adapterPosition, "Add")
            }

        }
    }

    fun setOnClickListener(onClickListener: onClickView) {
        this.onClick = onClickListener
    }

    interface onClickView {
        fun onClickView(position: Int, type: String)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}