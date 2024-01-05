package com.ppk.currencyxchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ppk.currencyxchanger.data.entity.CurrencyRateEntity
import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel

@Dao
interface CurrencyRateDao {

    @Query("SELECT * FROM currency_rate")
    fun findAll(): List<CurrencyRateEntity>

    @Query(
        "SELECT * FROM currency_rate INNER JOIN supported_currency" +
                " ON supported_currency.currencyCode=currency_rate.currencyCode WHERE currency_rate.source=:source"
    )
    fun getAllCurrencyRateWithCountryName(source: String): List<CurrencyRateDomainModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRate(rates: List<CurrencyRateEntity>)

}