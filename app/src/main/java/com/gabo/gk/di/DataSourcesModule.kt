package com.gabo.gk.di

import com.gabo.gk.data.global.dataSources.auth.AuthGlobalDataSource
import com.gabo.gk.data.global.dataSources.auth.AuthGlobalDataSourceImpl
import com.gabo.gk.data.global.dataSources.images.ImagesGlobalDataSource
import com.gabo.gk.data.global.dataSources.images.ImagesGlobalDataSourceImpl
import com.gabo.gk.data.global.dataSources.product.ProductGlobalDataSource
import com.gabo.gk.data.global.dataSources.product.ProductGlobalDataSourceImpl
import com.gabo.gk.data.global.dataSources.users.UsersGlobalDataSource
import com.gabo.gk.data.global.dataSources.users.UsersGlobalDataSourceImpl
import com.gabo.gk.data.local.dataSources.ProductLocalDataSource
import com.gabo.gk.data.local.dataSources.ProductLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(authGlobalDataSourceImpl: AuthGlobalDataSourceImpl): AuthGlobalDataSource

    @Binds
    @Singleton
    abstract fun bindProductGlobalDataSource(productGlobalDataSourceImpl: ProductGlobalDataSourceImpl): ProductGlobalDataSource

    @Binds
    @Singleton
    abstract fun bindProductLocalDataSource(productLocalDataSourceImpl: ProductLocalDataSourceImpl): ProductLocalDataSource

    @Binds
    @Singleton
    abstract fun bindImagesLocalDataSource(imagesGlobalDataSourceImpl: ImagesGlobalDataSourceImpl): ImagesGlobalDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(usersGlobalDataSourceImpl: UsersGlobalDataSourceImpl): UsersGlobalDataSource
}