package com.ppk.currencyxchanger.data.mapper

import com.ppk.currencyxchanger.data.entity.CurrencyRateEntity
import com.ppk.currencyxchanger.data.responseModel.CurrencyRateResponseModel

fun CurrencyRateResponseModel.toEntity(): List<CurrencyRateEntity> {
    val list = ArrayList<CurrencyRateEntity>()
    if (!rates.isNullOrEmpty()) {
        val keys = rates.keys
        for (key in keys) {
            var k = key

            //removing source currency for USDUSD
            if (!key.endsWith(source ?: "USD", true)) {
                k = key.replace(source ?: "USD", "", true)
            } else {
                k = source ?: "USD"
            }
            val rate = CurrencyRateEntity(k, source ?: "", rates[key] ?: 0.0)
            list.add(rate)
        }
    }
    return list
}
