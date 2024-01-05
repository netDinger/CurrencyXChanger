package com.ppk.currencyxchanger.presentation.model

data class CurrencyRateUiModel(
    var currencyCode: String,
    var countryName: String,
    var rate: Double = 0.0
)