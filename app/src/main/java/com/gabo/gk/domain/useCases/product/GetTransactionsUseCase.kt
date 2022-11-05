package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.model.TransactionModelDomain
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.ui.modelTransformers.toTransactionDomain
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : BaseUseCase<Unit, Resource<List<TransactionModelDomain>>> {
    override suspend fun invoke(params: Unit): Resource<List<TransactionModelDomain>> {
        try {
            val products = mutableListOf<ProductModelDomain>()
            when (val result = getUserUseCase(auth.currentUser!!.uid)) {
                is Resource.Success -> {
                    result.data?.purchasedProducts?.forEach {
                        products.add(it)
                    }
                    result.data?.soldProducts?.forEach {
                        products.add(it)
                    }
                }
                is Resource.Error -> return Resource.Error(result.errorMsg)
            }
            return if (products.isNotEmpty()) Resource.Success(products.map { it.toTransactionDomain() })
            else Resource.Error(context.getString(R.string.something_went_wrong))

        } catch (e: Error) {
            return Resource.Error(e.message)
        }
    }
}