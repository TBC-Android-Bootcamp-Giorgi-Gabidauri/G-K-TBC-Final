package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetSavedProductsFromServerUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : BaseUseCase<Unit, Resource<List<ProductModelDomain>>> {
    override suspend fun invoke(params: Unit): Resource<List<ProductModelDomain>> {
        return try {
            var notifications: List<ProductModelDomain> = emptyList()
            when (val user = getUserUseCase(auth.currentUser!!.uid)) {
                is Resource.Success -> notifications =
                    user.data?.savedProducts?.toMutableList() ?: emptyList()
                is Resource.Error -> return Resource.Error(user.errorMsg!!)
            }
            if (notifications.isNotEmpty()) {
                Resource.Success(notifications)
            } else {
                Resource.Error(context.getString(R.string.something_went_wrong))
            }
        } catch (e: Error) {
            Resource.Error(e.message)
        }
    }
}