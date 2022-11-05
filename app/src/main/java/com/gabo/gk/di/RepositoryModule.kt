package com.gabo.gk.di

import com.gabo.gk.data.repository.AuthRepositoryImpl
import com.gabo.gk.data.repository.ImagesRepositoryImpl
import com.gabo.gk.data.repository.ProductRepositoryImpl
import com.gabo.gk.data.repository.UserRepositoryImpl
import com.gabo.gk.domain.repository.AuthRepository
import com.gabo.gk.domain.repository.ImagesRepository
import com.gabo.gk.domain.repository.ProductRepository
import com.gabo.gk.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}