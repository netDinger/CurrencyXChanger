package com.ppk.currencyxchanger.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ppk.currencyxchanger.data.entity.SupportedCurrencyEntity

@Dao
interface SupportedCurrenciesDao {

    @Query("SELECT * FROM supported_currency")
    fun findAll(): List<SupportedCurrencyEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupportedCurrencies(currencies: List<SupportedCurrencyEntity>)

}