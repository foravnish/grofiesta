package com.ananda.retailer.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ananda.retailer.Room.Tables.MyCart

@Dao
interface MyCartDao {

    @Query("SELECT * FROM MyCart_TB WHERE product_id=:product_id")
    fun getMyCart(product_id:String): LiveData<List<MyCart>>

    @Query("SELECT * FROM MyCart_TB")
    fun getAllMyCart(): LiveData<List<MyCart>>

//    @Query("SELECT * FROM MyCart_TB WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID= :itemErpID")
//    fun getItemFromCart(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String
//    ): List<MyCart>

//    @Query("UPDATE MyCart_TB SET deliveryDate = :deliveryDate WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID = :itemErpID")
//    fun updateDeliveryDate(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String?,
//        deliveryDate: String
//    )

//    @Query("UPDATE MyCart_TB SET qty = :qty, totalAmount = :totalAmount WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID = :itemErpID")
//    fun updateSalePriceAndMrp(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String?, qty: Int,
//        totalAmount: Double
//    )

    @Query("UPDATE MyCart_TB SET qty = :qty, totalAmount = :totalAmount WHERE product_id= :product_id")
    fun updateCartItem(
        qty: String, totalAmount: String, product_id: String)

    @Query("DELETE FROM MyCart_TB WHERE product_id= :product_id")
    fun deleteItemFromCart(
        product_id: String
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: MyCart): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(mData: ArrayList<MyCart>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(mData: MyCart)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateList(mData: ArrayList<MyCart>)

    @Query("DELETE FROM MyCart_TB")
    fun deleteAll()

    @Transaction
    fun insertOrUpdate(objList: MyCart) {
        val insertResult = insert(objList)
        if (insertResult == -1L) update(objList)
    }

    @Transaction
    fun insertOrUpdate(objList: ArrayList<MyCart>) {
        try {
            val insertResult = insertList(objList)
            val updateList = ArrayList<MyCart>()
            for (i in insertResult.indices) {
                if (insertResult[i] == -1L) updateList.add(objList[i])
            }
            if (updateList.isNotEmpty()) updateList(updateList)
        } catch (e: Exception) {
        }
    }

}