package com.gabo.gk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabo.gk.comon.constants.TABLE_NAME
import com.gabo.gk.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: ProductEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE id=:id")
    suspend fun deleteItem(id: String)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getItems(): Flow<List<ProductEntity>>
}