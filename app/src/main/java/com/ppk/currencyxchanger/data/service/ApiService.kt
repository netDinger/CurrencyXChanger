package com.ppk.currencyxchanger.data.service

import com.ppk.currencyxchanger.data.responseModel.CurrencyRateResponseModel
import com.ppk.currencyxchanger.data.responseModel.SupportedCurrencyResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/list")
    suspend fun getSupportedCountries(@Query("access_key") accessKey: String): Response<SupportedCurrencyResponseModel>


    @GET("/live")
    suspend fun getCurrencyRate(
        @Query("access_key") accessKey: String,
        @Query("source") sourceCurrency: String
    ): Response<CurrencyRateResponseModel>
//
//    @GET("/live")
//    suspend fun getCurrencyRate(@Query("access_key") accessKey:String): Response<CurrencyRateResponseModel>

}