package com.ananda.retailer.Room.Tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "MyCart_TB",
//    indices = [Index(
//        value = ["mainCategoryErpID", "subCategoryErpID", "sellerDivisionSr", "isDirectConsumer", "itemErpID"],
//        unique = true
//    )]
)
data class MyCart(
    @field:ColumnInfo(name = "product_id")
    val product_id: String,
    @field:ColumnInfo(name = "category_id")
    val category_id: String,
    @field:ColumnInfo(name = "sub_category_id")
    val sub_category_id: String,
    @field:ColumnInfo(name = "product_name")
    val product_name: String,
    @field:ColumnInfo(name = "weight_size")
    val weight_size: String,
    @field:ColumnInfo(name = "main_price")
    val main_price: String,
    @field:ColumnInfo(name = "display_price")
    val display_price: String,
    @field:ColumnInfo(name = "purchase_price")
    val purchase_price: String,
    @field:ColumnInfo(name = "discount_percent")
    val discount_percent: String,
    @field:ColumnInfo(name = "description")
    val description: String,
    @field:ColumnInfo(name = "short_desp")
    val short_desp: String,
    @field:ColumnInfo(name = "image")
    val image: String,
    @field:ColumnInfo(name = "qty")
    val qty: String,
    @field:ColumnInfo(name = "gst")
    val gst: String,
    @field:ColumnInfo(name = "category_name")
    val category_name: String,
    @field:ColumnInfo(name = "totalAmount")
    val totalAmount: String,
) : Serializable {
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "srno")
    var srno: Int = 0
}
