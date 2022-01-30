package com.ananda.retailer.Views.Activities.Grocery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ananda.retailer.Room.Repo.GroceryDBRepository
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.room.response.MyCartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroceryViewModel(application: Application) : AndroidViewModel(application) {

    private var dbRepo: GroceryDBRepository? = null


    private val _cartItemLiveData = MutableLiveData<MyCart>()
    var cartItemLiveData: LiveData<MyCart>? = _cartItemLiveData

    init {
        initRepo()
    }

    private fun initRepo() {
        if (dbRepo == null) dbRepo = GroceryDBRepository().getInstance()
    }

    /**My Cart*/
    fun insertItemInCart(data: MyCartResponse) {
        data.apply {
            MyCart(
                product_id,
                category_id,
                sub_category_id,
                product_name,
                weight_size,
                main_price,
                display_price,
                purchase_price,
                discount_percent,
                description,
                short_desp,
                image,
                qty,
                gst,
                category_name,
                display_price
            ).let { _insertItemInCart(it) }
        }
    }

    fun insertItemInWishList(data: MyCartResponse) {
        data.apply {
            MyWishList(
                product_id,
                category_id,
                sub_category_id,
                product_name,
                weight_size,
                main_price,
                display_price,
                purchase_price,
                discount_percent,
                description,
                short_desp,
                image,
                qty,
                gst,
                category_name,
                display_price
            ).let { _insertItemInWishList(it) }
        }
    }

    val totalAmount: (Double, Int) -> Double = { amt: Double, qty: Int -> amt * qty }

    private fun _insertItemInCart(data: MyCart) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                dbRepo?.insertItemInCart(data)
            }
//            getItemFromCart(
//                data.itemName, data.itemPrice
//            )
        }
    }

    private fun _insertItemInWishList(data: MyWishList) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                dbRepo?.insertItemInWishList(data)
            }
//            getItemFromCart(
//                data.itemName, data.itemPrice
//            )
        }
    }

    fun getMyCart(product_id:String): LiveData<List<MyCart>> {
        return dbRepo!!.getMyCart(product_id)
    }
    fun getAllMyCart(): LiveData<List<MyCart>> {
        return dbRepo!!.getAllMyCart()
    }


    fun getMySignelWishList(product_id:String): LiveData<List<MyWishList>> {
        return dbRepo!!.getSinglelWishList(product_id)
    }

    fun getMySignelWishList(): LiveData<List<MyWishList>> {
        return dbRepo!!.getAllWishList()
    }

    fun updateCartItem(data: MyCart, isPlus: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            data.apply {
                var _qty = qty.toInt()
                _qty = if (isPlus) _qty + 1 else _qty - 1
                if (_qty >= 1)
                    dbRepo!!.updateCartItem(
                        _qty.toString(), (display_price.toInt()*_qty).toString(), product_id
                    )
                else deleteItemFromCart(data)
                getMyCart(product_id)
            }
        }
    }

//    fun updateCartItemValue(data: MyCart, value: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            data.apply {
//                    dbRepo!!.updateCartItem(
//                        mainCategoryErpID, subCategoryErpID, sellerDivisionSr, isDirectConsumer,
//                        itemErpID, value.toInt(), salePrice * value.toInt()
//                    )
//                getItemFromCart(mainCategoryErpID, subCategoryErpID, itemErpID)
//            }
//        }
//    }


//    private fun editCartItemQty(data: MyCart) {
//        viewModelScope.launch(Dispatchers.IO) {
//            data.apply {
//                dbRepo!!.updateCartItem(
//                    mainCategoryErpID, subCategoryErpID, sellerDivisionSr, isDirectConsumer,
//                    itemErpID, qty, salePrice * qty
//                )
//                getItemFromCart(mainCategoryErpID, subCategoryErpID, itemErpID)
//            }
//        }
//    }

    fun deleteItemFromCart(data: MyCart) {
        viewModelScope.launch(Dispatchers.IO) {
            data.apply {
                dbRepo!!.deleteItemFromCart(
                    product_id
                )
//                getItemFromCart(mainCategoryErpID, subCategoryErpID, itemErpID)
            }
        }
    }

    fun deleteItemFromCart(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo!!.deleteItemFromCart(product_id)
        }
    }

    fun deleteItemFromWishList(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo!!.deleteItemFromWishList(product_id)
        }
    }


    fun deleteMyCartAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo!!.deleteAll()
        }
    }

//    private fun getItemFromCart(categoryID: String, subCategoryID: String, itemErpID: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = dbRepo!!.getItemFromCart(
//                categoryID, subCategoryID, _sellerDivisionSr, _isDirectCustomer, itemErpID
//            )
//            if (result != null && result.isNotEmpty()) _cartItemLiveData.postValue(result[0])
//        }
//    }

//    fun getCartDetails(): LiveData<CartDetails> {
//        return dbRepo!!.getCartDetails(_sellerDivisionSr, _isDirectCustomer)
//    }


}