package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.FilterModelDomain
import com.gabo.gk.domain.model.ProductModelDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseUseCase<Pair<FilterModelDomain, List<ProductModelDomain>>, Flow<Resource<List<ProductModelDomain>>>> {
    val list = mutableListOf<ProductModelDomain>()
    private var errorMsg = ""
    private val statusFiltered = mutableListOf<ProductModelDomain>()
    private val priceFiltered = mutableListOf<ProductModelDomain>()
    private val locationFiltered = mutableListOf<ProductModelDomain>()
    override suspend fun invoke(params: Pair<FilterModelDomain, List<ProductModelDomain>>): Flow<Resource<List<ProductModelDomain>>> =
        flow {
            try {
                errorMsg = ""
                list.clear()
                params.second.forEach { list.add(it) }
                filterByStatus(params.first)
                 filterByPrice(params.first)
                filterByLocation(params.first)

                if (errorMsg.isNotEmpty()) {
                    emit(Resource.Error(errorMsg))
                } else {
                    when (params.first.showFirst) {
                        context.getString(R.string.low_price) -> {
                            emit(Resource.Success(locationFiltered.sortedBy { it.price }))
                        }
                        context.getString(R.string.low_price) -> {
                            emit(Resource.Success(locationFiltered.sortedByDescending { it.price }))
                        }
                        else -> emit(Resource.Success(locationFiltered))
                    }
                }

            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }

    private fun filterByLocation(filter: FilterModelDomain) {
        locationFiltered.clear()
        when (filter.location) {
            context.getString(R.string.any_location) -> priceFiltered.forEach {
                locationFiltered.add(
                    it
                )
            }
            else -> priceFiltered.forEach {
                if (it.location == filter.location) {
                    locationFiltered.add(it)
                }
            }
        }
    }

    private fun filterByPrice(filter: FilterModelDomain) {
        priceFiltered.clear()
        when {
            filter.priceFrom != 0 || filter.priceTill != 999999999 -> {
                when {
                    filter.priceFrom != 0 -> {
                        if (filter.priceTill != 999999999) {
                            if (filter.priceFrom <= filter.priceTill) {
                                statusFiltered.forEach {
                                    if (filter.priceFrom <= it.price && it.price <= filter.priceTill) {
                                        priceFiltered.add(it)
                                    }
                                }
                            } else {
                                errorMsg = context.getString(R.string.choose_correct_price_range)
                            }
                        } else {
                            statusFiltered.forEach {
                                if (filter.priceFrom <= it.price) {
                                    priceFiltered.add(it)
                                }
                            }
                        }
                    }
                    else -> {
                        statusFiltered.forEach {
                            if (it.price <= filter.priceTill) {
                                priceFiltered.add(it)
                            }
                        }
                    }
                }
            }
            else -> when {
                filter.anyPrice -> {
                    statusFiltered.forEach { priceFiltered.add(it) }
                }
                filter.negotiablePrice -> {
                    statusFiltered.forEach {
                        if (it.negotiablePrice) {
                            priceFiltered.add(it)
                        }
                    }
                }
                else -> {
                    statusFiltered.forEach {
                        if (!it.negotiablePrice) {
                            priceFiltered.add(it)
                        }
                    }
                }
            }
        }
    }

    private fun filterByStatus(filter: FilterModelDomain) {
        statusFiltered.clear()
        when (filter.status) {
            context.getString(R.string.any) -> list.forEach { statusFiltered.add(it) }
            else -> list.forEach {
                if (it.productCondition == filter.status) {
                    statusFiltered.add(it)
                }
            }
        }
    }
}