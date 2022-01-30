package com.ananda.retailer.Room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.ananda.retailer.Room.Dao.MyCartDao
import com.ananda.retailer.Room.Dao.WishListtDao
import com.ananda.retailer.Room.Tables.MyCart
import com.ananda.retailer.Room.Tables.MyWishList
import com.app.grofiesta.App


@Database(
    entities = [MyCart::class, MyWishList::class],
    version = 1, exportSchema = false
)

abstract class CanDatabase : RoomDatabase() {


    abstract fun getMyCart(): MyCartDao
    abstract fun getMyWishList(): WishListtDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {}

    companion object {
        @Volatile
        var INSTANCE: CanDatabase? = null
        val DB_NAME = "GrofiestaApp"

        fun getDatabase(): CanDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(
                        App.instance.applicationContext, CanDatabase::class.java, DB_NAME
                    ).fallbackToDestructiveMigration().build()
                return INSTANCE!!
            }
        }


    }
}

