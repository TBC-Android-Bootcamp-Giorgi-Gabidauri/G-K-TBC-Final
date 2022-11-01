package com.gabo.gk.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabo.gk.comon.constants.PRODUCTS_DATABASE_NAME
import com.gabo.gk.data.local.dao.ProductsDao
import com.gabo.gk.data.local.database.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): ProductsDatabase {
        return Room.databaseBuilder(application, ProductsDatabase::class.java, PRODUCTS_DATABASE_NAME)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(dataBase: ProductsDatabase): ProductsDao {
        return dataBase.getPurchaseDao
    }
}