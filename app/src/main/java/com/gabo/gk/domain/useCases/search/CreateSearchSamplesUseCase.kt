package com.gabo.gk.domain.useCases.search

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.helpers.QueryHelper
import javax.inject.Inject

class CreateSearchSamplesUseCase @Inject constructor(private val queryHelper: QueryHelper) :
    BaseUseCase<String, List<String>> {
    override suspend fun invoke(params: String): List<String> {
       return queryHelper.createSearchSamples(params)
    }
}