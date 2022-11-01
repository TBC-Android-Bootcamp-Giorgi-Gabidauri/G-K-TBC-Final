package com.gabo.gk.comon.helpers

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface QueryHelper {

    fun createSearchSamples(query: String): List<String>

    suspend fun queryHelper(
        error: String = "",
        query: (suspend () -> QuerySnapshot)?
    ): Flow<Resource<List<ProductModelDomain>>>
}