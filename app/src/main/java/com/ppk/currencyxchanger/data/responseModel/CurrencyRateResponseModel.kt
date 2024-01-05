package com.ppk.currencyxchanger.data.responseModel

import com.google.gson.annotations.SerializedName

data class CurrencyRateResponseModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("timestamp") val timestamp: String,
    var source: String? = null,
    @SerializedName("quotes") val rates: Map<String, Double>? = null
)