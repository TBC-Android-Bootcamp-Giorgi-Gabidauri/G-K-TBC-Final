package com.gabo.gk.di

import com.gabo.gk.data.repository.AuthRepositoryImpl
import com.gabo.gk.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository (authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}