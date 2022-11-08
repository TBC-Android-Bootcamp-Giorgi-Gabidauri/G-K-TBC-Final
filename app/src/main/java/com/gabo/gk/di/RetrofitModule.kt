package com.gabo.gk.di

import com.gabo.gk.comon.constants.BASE_URL
import com.gabo.gk.data.global.notification.apis.ProductNotificationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): ProductNotificationApi {
        return retrofit.create(ProductNotificationApi::class.java)
    }
}