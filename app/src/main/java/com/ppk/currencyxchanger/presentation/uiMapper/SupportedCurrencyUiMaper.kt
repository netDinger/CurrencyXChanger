package com.ppk.currencyxchanger.presentation.uiMapper

import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel
import com.ppk.currencyxchanger.presentation.model.SupportedCurrencyUiModel

fun SupportedCurrencyDomainModel.toUiModel(): SupportedCurrencyUiModel {
    return SupportedCurrencyUiModel(currencyCode = currencyCode, countryName = countryName)
}