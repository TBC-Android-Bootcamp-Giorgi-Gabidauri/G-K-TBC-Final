package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.domain.useCases.user.UpdateUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BuyProductScenario @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : BaseUseCase<ProductModelDomain, String> {
    override suspend fun invoke(params: ProductModelDomain): String {
        return try {
            params.sold = true
            var clientUser: UserModelDomain? = null
            var sellerUser: UserModelDomain? = null
            when (val clientResult = getUserUseCase(auth.currentUser!!.uid)) {
                is Resource.Success -> clientUser = clientResult.data
                is Resource.Error -> return clientResult.errorMsg!!
            }
            when (val sellerResult = getUserUseCase(params.uid)) {
                is Resource.Success -> sellerUser = sellerResult.data
                is Resource.Error -> return sellerResult.errorMsg!!
            }
            params.purchasedBy = auth.currentUser!!.uid
            clientUser?.let { client ->
                client.availableAmount = client.availableAmount - params.price
                client.purchasedProducts.add(params)
            }
            sellerUser?.let { seller ->
                seller.availableAmount = seller.availableAmount + params.price
                seller.soldProducts.add(params)
            }
            updateProductUseCase(params)
            val updatedClient = updateUserUseCase(clientUser!!)
            val updatedSeller = updateUserUseCase(sellerUser!!)
            return context.getString(
                if (updatedClient == context.getString(R.string.user_updated_successfully) &&
                    updatedSeller == context.getString(R.string.user_updated_successfully)
                ) R.string.product_bought_successfully else R.string.something_went_wrong
            )
        } catch (e: Exception) {
            e.message.toString()
        }
    }
}