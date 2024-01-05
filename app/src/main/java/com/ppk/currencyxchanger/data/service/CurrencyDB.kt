package com.ppk.currencyxchanger.data.service


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ppk.currencyxchanger.data.dao.CurrencyRateDao
import com.ppk.currencyxchanger.data.dao.SupportedCurrenciesDao
import com.ppk.currencyxchanger.data.entity.CurrencyRateEntity
import com.ppk.currencyxchanger.data.entity.SupportedCurrencyEntity


@Database(
    entities = [CurrencyRateEntity::class, SupportedCurrencyEntity::class],
    version = 2, exportSchema = false
)

abstract class CurrencyDB : RoomDatabase() {
    abstract val countriesDao: SupportedCurrenciesDao
    abstract val currencyRateDao: CurrencyRateDao
}