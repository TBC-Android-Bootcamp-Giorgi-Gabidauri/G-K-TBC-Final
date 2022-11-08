package com.gabo.gk.data.global.notification.apis

import com.gabo.gk.comon.constants.CONTENT_TYPE
import com.gabo.gk.comon.constants.SERVER_KEY
import com.gabo.gk.data.global.notification.model.product.ProductPushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProductNotificationApi {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: ProductPushNotification
    ): Response<ResponseBody>
}