package com.ppk.currencyxchanger.domain.repository

import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel
import com.ppk.currencyxchanger.util.AppResult

interface CurrencyRepository {
    suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrencyDomainModel>?>
    suspend fun getCurrencyRate(sourceCurrency: String): AppResult<List<CurrencyRateDomainModel>>

}