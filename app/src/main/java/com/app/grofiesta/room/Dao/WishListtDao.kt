package com.ananda.retailer.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ananda.retailer.Room.Tables.MyWishList

@Dao
interface WishListtDao {

    @Query("SELECT * FROM WishList_TB WHERE product_id=:product_id")
    fun getMyWishListSingle(product_id:String): LiveData<List<MyWishList>>

    @Query("SELECT * FROM WishList_TB")
    fun getAllMyWishList(): LiveData<List<MyWishList>>

//    @Query("SELECT * FROM MyWishList WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID= :itemErpID")
//    fun getItemFromCart(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String
//    ): List<MyWishList>

//    @Query("UPDATE MyWishList SET deliveryDate = :deliveryDate WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID = :itemErpID")
//    fun updateDeliveryDate(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String?,
//        deliveryDate: String
//    )

//    @Query("UPDATE MyWishList SET qty = :qty, totalAmount = :totalAmount WHERE sellerDivisionSr= :arg2 AND isDirectConsumer= :arg3 AND mainCategoryErpID= :arg0 AND subCategoryErpID= :arg1 AND itemErpID = :itemErpID")
//    fun updateSalePriceAndMrp(
//        arg0: String, arg1: String, arg2: String, arg3: String, itemErpID: String?, qty: Int,
//        totalAmount: Double
//    )

    @Query("UPDATE WishList_TB SET qty = :qty, totalAmount = :totalAmount WHERE product_id= :product_id")
    fun updateCartItem(
        qty: String, totalAmount: String, product_id: String)

    @Query("DELETE FROM WishList_TB WHERE product_id= :product_id")
    fun deleteItemFromWishList(
        product_id: String
    )

//    @Query("DELETE FROM MyWishList WHERE sellerDivisionSr= :arg0 AND isDirectConsumer= :arg1 AND itemErpID = :itemErpID")
//    fun deleteItemFromCart(arg0: String, arg1: String, itemErpID: String?)

//    @Query("SELECT SUM(totalAmount) as _totalAmount, SUM(qty) as _qty, Count(*) as _itemCount FROM MyWishList WHERE sellerDivisionSr= :arg0 AND isDirectConsumer= :arg1")
//    fun getCartDetails(arg0: String, arg1: String): LiveData<CartDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: MyWishList): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(mData: ArrayList<MyWishList>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(mData: MyWishList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateList(mData: ArrayList<MyWishList>)

    @Query("DELETE FROM WishList_TB")
    fun deleteAll()

    @Transaction
    fun insertOrUpdate(objList: MyWishList) {
        val insertResult = insert(objList)
        if (insertResult == -1L) update(objList)
    }

    @Transaction
    fun insertOrUpdate(objList: ArrayList<MyWishList>) {
        try {
            val insertResult = insertList(objList)
            val updateList = ArrayList<MyWishList>()
            for (i in insertResult.indices) {
                if (insertResult[i] == -1L) updateList.add(objList[i])
            }
            if (updateList.isNotEmpty()) updateList(updateList)
        } catch (e: Exception) {
        }
    }

}