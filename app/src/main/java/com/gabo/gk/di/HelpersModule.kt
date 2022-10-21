package com.gabo.gk.di

import com.gabo.gk.comon.helpers.HashModelHelper
import com.gabo.gk.comon.helpers.HashModelHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HelpersModule {
    @Binds
    @Singleton
    abstract fun bindHashModelHelper(hashModelHelperImpl: HashModelHelperImpl): HashModelHelper
}