package com.ppk.currencyxchanger.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ppk.currencyxchanger.data.dao.CurrencyRateDao
import com.ppk.currencyxchanger.data.dao.SupportedCurrenciesDao
import com.ppk.currencyxchanger.data.service.CurrencyDB
import com.ppk.currencyxchanger.data.service.PrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): CurrencyDB {
        return Room.databaseBuilder(application, CurrencyDB::class.java, "currencies")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyListDao(database: CurrencyDB): SupportedCurrenciesDao {
        return database.countriesDao
    }

    @Provides
    @Singleton
    fun provideCurrencyRateDao(database: CurrencyDB): CurrencyRateDao {
        return database.currencyRateDao
    }

    @Provides
    @Singleton
    fun providePrefService(context: Context): PrefService {
        return PrefService(context)
    }


}