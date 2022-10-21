package com.gabo.gk.domain.repository

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun uploadProduct(modelMap: HashMap<String, Any?>): Flow<String>
}