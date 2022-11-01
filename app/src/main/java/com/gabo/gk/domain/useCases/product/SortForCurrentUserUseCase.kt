package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.CurrentUserSortModelDomain
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortForCurrentUserUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<CurrentUserSortModelDomain, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: CurrentUserSortModelDomain): Flow<Resource<List<ProductModelDomain>>> {
        return productRepository.sortForCurrentUser(params.uid, params.field, params.equalsTo)
    }
}

