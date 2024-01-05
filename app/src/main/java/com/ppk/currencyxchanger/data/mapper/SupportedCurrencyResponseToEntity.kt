package com.ppk.currencyxchanger.data.mapper

import com.ppk.currencyxchanger.data.entity.SupportedCurrencyEntity
import com.ppk.currencyxchanger.data.responseModel.SupportedCurrencyResponseModel

fun SupportedCurrencyResponseModel.toEntity(): List<SupportedCurrencyEntity> {
    val list = ArrayList<SupportedCurrencyEntity>()
    if (!currencies.isNullOrEmpty()) {
        val keys = currencies.keys
        for (key in keys) {
            val rate = SupportedCurrencyEntity(key, currencies[key] ?: "")
            list.add(rate)
        }
    }
    return list
}
