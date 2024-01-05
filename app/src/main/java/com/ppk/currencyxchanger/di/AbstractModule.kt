package com.ppk.currencyxchanger.di

import com.ppk.currencyxchanger.data.repoImpl.CurrencyRepoImpl
import com.ppk.currencyxchanger.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {
    /**--------Repository----------**/
    @Binds
    abstract fun provideCurrencyRepo(currencyRepoImpl: CurrencyRepoImpl): CurrencyRepository
}