package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.notification.model.product.ProductNotificationData
import com.gabo.gk.data.global.notification.model.product.ProductPushNotification
import com.gabo.gk.domain.model.NotificationModel
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
    private val sendProductNotificationUseCase: SendProductNotificationUseCase,
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
                if(client.availableAmount >= params.price){
                    client.availableAmount = client.availableAmount - params.price
                    client.purchasedProducts.add(params)
                    if (clientUser.savedProducts.contains(params)) clientUser.savedProducts.remove(params)

                    sellerUser?.let { seller ->
                        seller.availableAmount = seller.availableAmount + params.price
                        seller.soldProducts.add(params)
                        seller.notifications.add(NotificationModel(
                            imgProduct = params.photos!![0],info = "${params.title} has been sold",
                            imgClient = clientUser.profileImg
                        ))
                    }
                }else{
                    return context.getString(R.string.not_enough_money)
                }
            }
            updateProductUseCase(params)

            ProductPushNotification(
                ProductNotificationData(
                    "${params.title} has been sold",
                    context.getString(R.string.click_to_See_all_notifications)
                ), sellerUser!!.token
            ).also {
                sendProductNotificationUseCase(it)
            }

            val updatedClient = updateUserUseCase(clientUser!!)
            val updatedSeller = updateUserUseCase(sellerUser)
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