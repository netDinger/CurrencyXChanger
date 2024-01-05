package com.ppk.currencyxchanger.data.repoImpl

import com.ppk.currencyxchanger.data.dataSource.CurrencyDataSource
import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel
import com.ppk.currencyxchanger.domain.repository.CurrencyRepository
import com.ppk.currencyxchanger.util.AppResult
import javax.inject.Inject

class CurrencyRepoImpl @Inject constructor(
    private val currencyDataSource: CurrencyDataSource
) : CurrencyRepository {
    override suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrencyDomainModel>?> {
        return currencyDataSource.getSupportedCurrencies()
    }

    override suspend fun getCurrencyRate(sourceCurrency: String): AppResult<List<CurrencyRateDomainModel>> {
        return currencyDataSource.getCurrencyRate(sourceCurrency)
    }
}