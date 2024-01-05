package com.ppk.currencyxchanger.domain.useCase

import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel
import com.ppk.currencyxchanger.domain.repository.CurrencyRepository
import com.ppk.currencyxchanger.util.AppResult
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrencyDomainModel>?> =
        currencyRepository.getSupportedCurrencies()

    suspend fun getCurrencyRate(sourceCurrency: String): AppResult<List<CurrencyRateDomainModel>> =
        currencyRepository.getCurrencyRate(sourceCurrency)

}