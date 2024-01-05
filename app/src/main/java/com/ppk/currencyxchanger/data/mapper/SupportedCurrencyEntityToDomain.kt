package com.ppk.currencyxchanger.data.mapper

import com.ppk.currencyxchanger.data.entity.SupportedCurrencyEntity
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel

fun SupportedCurrencyEntity.toDomain(): SupportedCurrencyDomainModel {
    return SupportedCurrencyDomainModel(
        currencyCode = currencyCode,
        countryName = countryName ?: ""
    )
}
