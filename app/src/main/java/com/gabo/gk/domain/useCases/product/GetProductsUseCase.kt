package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val auth: FirebaseAuth
) :
    BaseUseCase<Unit, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: Unit): Flow<Resource<List<ProductModelDomain>>> = flow {
        productRepository.getProducts().collect {
            try {
                when (it) {
                    is Resource.Error -> {
                        emit(Resource.Error(it.errorMsg))
                    }
                    is Resource.Success -> {
                        val list = it.data!!.toMutableList()
                        it.data.forEach { product ->
                            if (product.uid == auth.currentUser!!.uid || product.sold) {
                                list.remove(product)
                            }
                        }
                        emit(Resource.Success(list.toList()))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}