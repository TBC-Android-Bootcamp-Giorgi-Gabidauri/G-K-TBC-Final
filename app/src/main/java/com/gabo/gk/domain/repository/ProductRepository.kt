package com.gabo.gk.domain.repository

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.notification.model.product.ProductPushNotification
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<ProductModelDomain>>>
    suspend fun uploadProduct(model: ProductModelDomain): Flow<String>
    suspend fun sortProducts(
        field: String,
        equalsTo: String
    ): Flow<Resource<List<ProductModelDomain>>>

    suspend fun sortForCurrentUser(
        uid: String,
        field: String,
        equalsTo: Any?
    ): Flow<Resource<List<ProductModelDomain>>>

    suspend fun searchProducts(
        field: String,
        query: String
    ): Flow<Resource<List<ProductModelDomain>>>

    suspend fun getSavedProductsFromDb(): Flow<List<ProductModelDomain>>
    suspend fun saveProductToDb(product: ProductModelDomain)
    suspend fun deleteProductFromDb(id: String)
    suspend fun updateProduct(newProduct: ProductModelDomain): String
    suspend fun deleteProduct(newProduct: ProductModelDomain): String
    suspend fun sendNotification(notification: ProductPushNotification)
    suspend fun deleteAllProductsFromDb()
}