package com.gabo.gk.data.local.dataSources

import com.gabo.gk.data.local.database.ProductsDatabase
import com.gabo.gk.data.transformers.toDomain
import com.gabo.gk.data.transformers.toEntity
import com.gabo.gk.domain.model.ProductModelDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val database: ProductsDatabase
) {
    fun getSavedProducts(): Flow<List<ProductModelDomain>> {
        return database.getPurchaseDao.getItems().map { it.map { entity -> entity.toDomain() } }
    }

    suspend fun saveProduct(product: ProductModelDomain) { database.getPurchaseDao.saveItem(product.toEntity()) }

    suspend fun deleteProduct(id: String) { database.getPurchaseDao.deleteItem(id) }

    suspend fun deleteAll(){ database.getPurchaseDao.deleteAll() }
}