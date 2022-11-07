package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedProductsUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<Pair<String,String>, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: Pair<String,String>): Flow<Resource<List<ProductModelDomain>>> = flow{
        productRepository.sortProducts(params.first,params.second).map {
            when(it){
                is Resource.Success ->{
                    val data = mutableListOf<ProductModelDomain>()
                    it.data?.map { product -> if(!product.sold) data.add(product) }
                    emit(Resource.Success(data))
                }
                is Resource.Error -> emit(Resource.Error(it.errorMsg))
            }
        }
    }
}