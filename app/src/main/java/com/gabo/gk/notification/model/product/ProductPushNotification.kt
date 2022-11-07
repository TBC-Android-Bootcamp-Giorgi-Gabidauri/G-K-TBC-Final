package com.gabo.gk.notification.model.product

data class ProductPushNotification(
    val data: ProductNotificationData,
    val to: String
)
