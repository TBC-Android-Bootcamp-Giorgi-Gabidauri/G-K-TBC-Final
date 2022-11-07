package com.gabo.gk.data.global.dataSources.product

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.dto.ProductDto
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.notification.model.product.ProductPushNotification
import kotlinx.coroutines.flow.Flow

interface ProductGlobalDataSource {
    suspend fun uploadProduct(model: ProductModelDomain): Flow<String>
    suspend fun getProducts(): Flow<Resource<List<ProductModelDomain>>>
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

    suspend fun updateProduct(product: ProductDto): String
    suspend fun deleteProduct(product: ProductDto): String
    suspend fun sendNotification(notification: ProductPushNotification)
}