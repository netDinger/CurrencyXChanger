package com.ppk.currencyxchanger.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyRateDomainModel(
    var currencyCode: String,
    var countryName: String,
    var source: String,
    var rate: Double = 0.0
) : Parcelable