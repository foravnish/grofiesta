package com.ananda.retailer.Room.Repo

import androidx.lifecycle.LiveData
import com.ananda.retailer.Room.CanDatabase
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.room.table.CartDetails


class GroceryDBRepository {

    private var mRepository: GroceryDBRepository? = null

    var databaseHelper: CanDatabase? = null

    private fun initializeDB(): CanDatabase {
        if (CanDatabase.INSTANCE != null) return CanDatabase.INSTANCE!!
        return CanDatabase.getDatabase()
    }

    init {
        databaseHelper = initializeDB()
    }

    fun getInstance(): GroceryDBRepository {
        if (mRepository == null) mRepository = GroceryDBRepository()
        return mRepository as GroceryDBRepository
    }

    /**MyCart*/
    fun insertItemInCart(data: MyCart) {
        databaseHelper?.getMyCart()?.insertOrUpdate(data)
    }

    fun getMyCart(product_id:String): LiveData<List<MyCart>> {
        return databaseHelper?.getMyCart()?.getMyCart(product_id)!!
    }

    fun getAllMyCart(): LiveData<List<MyCart>> {
        return databaseHelper?.getMyCart()?.getAllMyCart()!!
    }

    fun getAllWishList(): LiveData<List<MyWishList>> {
        return databaseHelper?.getMyWishList()?.getAllMyWishList()!!
    }
    fun getSinglelWishList(product_id:String): LiveData<List<MyWishList>> {
        return databaseHelper?.getMyWishList()?.getMyWishListSingle(product_id)!!
    }


//    fun getItemFromCart(
//        categoryID: String, subCategoryID: String, sellerDivisionSr_: String,
//        isDirectConsumer_: String, itemErpID: String
//    ): List<MyCart> {
//        return databaseHelper?.getMyCart()?.getItemFromCart(
//            categoryID, subCategoryID, sellerDivisionSr_, isDirectConsumer_, itemErpID
//        )!!
//    }

//    fun getCartDetails(
//        sellerDivisionSr_: String, isDirectConsumer_: String
//    ): LiveData<CartDetails> {
//        return databaseHelper?.getMyCart()?.getCartDetails(sellerDivisionSr_, isDirectConsumer_)!!
//    }

    fun updateCartItem(
        qty: String, totalAmount: String, product_id: String
    ) {
        databaseHelper?.getMyCart()?.updateCartItem(
            qty, totalAmount, product_id
        )!!
    }


    fun deleteItemFromCart(
        product_id: String
    ) {
        databaseHelper?.getMyCart()?.deleteItemFromCart(
            product_id
        )!!
    }

    fun deleteItemFromWishList(
        product_id: String
    ) {
        databaseHelper?.getMyWishList()?.deleteItemFromWishList(
            product_id
        )!!
    }

    fun insertItemInWishList(data: MyWishList) {
        databaseHelper?.getMyWishList()?.insertOrUpdate(data)
    }


    fun deleteAll() {
        databaseHelper?.getMyCart()?.deleteAll()!!
    }




}