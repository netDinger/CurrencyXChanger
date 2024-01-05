package com.ppk.currencyxchanger.presentation.uiMapper

import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel
import com.ppk.currencyxchanger.presentation.model.CurrencyRateUiModel

fun CurrencyRateDomainModel.toUiModel(): CurrencyRateUiModel {
    return CurrencyRateUiModel(currencyCode, countryName, rate)
}