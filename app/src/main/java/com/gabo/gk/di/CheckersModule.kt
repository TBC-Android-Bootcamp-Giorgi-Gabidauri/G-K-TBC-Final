package com.gabo.gk.di

import com.gabo.gk.comon.checkers.Checkers
import com.gabo.gk.data.repository.CheckersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CheckersModule {
    @Binds
    @Singleton
    abstract fun bindCheckers (checkersImpl: CheckersImpl): Checkers
}