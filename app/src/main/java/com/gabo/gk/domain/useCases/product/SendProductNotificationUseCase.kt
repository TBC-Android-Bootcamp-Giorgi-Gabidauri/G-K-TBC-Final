package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.ProductRepository
import com.gabo.gk.notification.model.product.ProductPushNotification
import javax.inject.Inject

class SendProductNotificationUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<ProductPushNotification, Unit> {
    override suspend fun invoke(params: ProductPushNotification) {
        productRepository.sendNotification(params)
    }
}