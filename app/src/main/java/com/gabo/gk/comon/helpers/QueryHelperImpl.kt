package com.gabo.gk.comon.helpers

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.dto.ProductDto
import com.gabo.gk.data.transformers.toDomain
import com.gabo.gk.domain.model.ProductModelDomain
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueryHelperImpl @Inject constructor() : QueryHelper {
    override suspend fun queryHelper(
        error: String,
        query: (suspend () -> QuerySnapshot)?
    ) = flow {
        try {
            val list = mutableListOf<ProductModelDomain>()
            val result = query?.invoke()
            result?.documents?.forEach {
                 var product = it.toObject<ProductDto>()
                product = product?.copy(id = it.id)
                product?.let { dto -> list.add(dto.toDomain()) }
            }
            if (list.isNotEmpty()) {
                emit(Resource.Success(list.toList()))
            } else {
                if (error.isNotEmpty()) emit(Resource.Error(error))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override fun createSearchSamples(query: String): List<String> {
        val list = mutableListOf<String>()
        var q = query
        for (i in 1..query.length) {
            for (n in 1..q.length) {
                list.add(q.take(n))
            }
            q = query.drop(i)
        }
        return list
    }
}