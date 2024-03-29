package com.ppk.currencyxchanger.data.responseModel

import com.google.gson.annotations.SerializedName

data class SupportedCurrencyResponseModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("currencies") val currencies: Map<String, String>?
)

