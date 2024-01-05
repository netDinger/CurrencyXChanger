package com.ppk.currencyxchanger.data.mapper

import com.ppk.currencyxchanger.data.responseModel.SupportedCurrencyResponseModel
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel

fun SupportedCurrencyResponseModel.toDomain(): List<SupportedCurrencyDomainModel> {
    val list = ArrayList<SupportedCurrencyDomainModel>()
    if (!currencies.isNullOrEmpty()) {
        val keys = currencies.keys
        for (key in keys) {
            val rate = SupportedCurrencyDomainModel(key, currencies[key] ?: "")
            list.add(rate)
        }
    }
    return list
}

