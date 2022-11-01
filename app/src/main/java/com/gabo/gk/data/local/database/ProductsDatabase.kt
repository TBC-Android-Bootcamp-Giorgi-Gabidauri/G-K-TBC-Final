package com.gabo.gk.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabo.gk.data.local.converters.ListOfStingsTypeConverter
import com.gabo.gk.data.local.dao.ProductsDao
import com.gabo.gk.data.local.entity.ProductEntity


@Database(entities = [ProductEntity::class], version = 8)
@TypeConverters(ListOfStingsTypeConverter::class)
abstract class ProductsDatabase : RoomDatabase() {
    abstract val getPurchaseDao: ProductsDao
}