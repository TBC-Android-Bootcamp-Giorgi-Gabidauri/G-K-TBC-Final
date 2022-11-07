package com.gabo.gk.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.gabo.gk.comon.constants.PREFS
import com.gabo.gk.comon.constants.PRODUCTS_DATABASE_NAME
import com.gabo.gk.data.local.dao.ProductsDao
import com.gabo.gk.data.local.database.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): ProductsDatabase {
        return Room.databaseBuilder(
            application,
            ProductsDatabase::class.java,
            PRODUCTS_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(dataBase: ProductsDatabase): ProductsDao {
        return dataBase.getPurchaseDao
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

}