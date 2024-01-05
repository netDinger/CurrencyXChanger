package com.ppk.currencyxchanger.data.dataSource

import android.content.Context
import com.ppk.currencyxchanger.BuildConfig
import com.ppk.currencyxchanger.data.dao.CurrencyRateDao
import com.ppk.currencyxchanger.data.dao.SupportedCurrenciesDao
import com.ppk.currencyxchanger.data.entity.SupportedCurrencyEntity
import com.ppk.currencyxchanger.data.mapper.toDomain
import com.ppk.currencyxchanger.data.mapper.toEntity
import com.ppk.currencyxchanger.data.responseModel.CurrencyRateResponseModel
import com.ppk.currencyxchanger.data.responseModel.SupportedCurrencyResponseModel
import com.ppk.currencyxchanger.data.service.ApiService
import com.ppk.currencyxchanger.data.service.PrefService
import com.ppk.currencyxchanger.domain.model.CurrencyRateDomainModel
import com.ppk.currencyxchanger.domain.model.SupportedCurrencyDomainModel
import com.ppk.currencyxchanger.util.AppResult
import com.ppk.currencyxchanger.util.NetworkManager.isOnline
import com.ppk.currencyxchanger.util.ResponseErrorHandler.handleResponseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//Since this is simple app, abstraction layer for data source is not necessary
//so i left this
/**
 * @author PPK
 * created on Jan 5,2024
 */
class CurrencyDataSource @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val supportedCurrenciesDao: SupportedCurrenciesDao,
    private val currencyRateDao: CurrencyRateDao,
    private val pref: PrefService
) {
    val accessKey = BuildConfig.ACCESS_KEY
    suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrencyDomainModel>?> {
        return withContext(Dispatchers.IO) {

            val localData = getSupportedCurrenciesFromCache()
            if (!localData.isNullOrEmpty()) {
                AppResult.Success(localData.map { it.toDomain() })
            } else {
                if (isOnline(context)) {
                    when (val result = getSupportedCurrenciesFromApi()) {
                        is AppResult.Success -> {
                            supportedCurrenciesDao.insertSupportedCurrencies(result.data.toEntity())
                            AppResult.Success(result.data.toDomain())
                        }

                        is AppResult.Failure ->
                            AppResult.Failure(result.error)
                    }

                } else {
                    AppResult.Failure(Exception("Please check you internet connection and try again!"))
                }
            }
        }
    }

    suspend fun getSupportedCurrenciesFromApi(): AppResult<SupportedCurrencyResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getSupportedCountries(accessKey = accessKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        AppResult.Success(it)
                    } ?: run {
                        AppResult.Failure(Exception("Error parsing body!"))
                    }

                } else {
                    handleResponseError(response)
                }
            } catch (e: Exception) {
                AppResult.Failure(e)
            }

        }
    }


    suspend fun getCurrencyRate(sourceCurrency: String): AppResult<List<CurrencyRateDomainModel>> {
        return withContext(Dispatchers.IO) {

            val localData = getCurrencyRateFromCache(sourceCurrency.ifEmpty { "USD" })
            if (!needToSync() && !localData.isNullOrEmpty()) {
                AppResult.Success(localData)

            } else {
                if (isOnline(context)) {
                    when (val result = getCurrencyRateFromApi(sourceCurrency)) {
                        is AppResult.Success -> {
                            currencyRateDao.insertCurrencyRate(result.data.toEntity())
                            pref.saveLastSyncedTime(System.currentTimeMillis())
                            val data = getCurrencyRateFromCache(sourceCurrency.ifEmpty { "USD" })
                            data?.let {
                                AppResult.Success(data)
                            } ?: run {
                                AppResult.Failure(Exception("Please check you internet connection and try again!"))
                            }
                        }

                        is AppResult.Failure ->
                            AppResult.Failure(result.error)
                    }

                } else {
                    AppResult.Failure(Exception("Please check you internet connection and try again!"))
                }
            }
        }
    }


    suspend fun getCurrencyRateFromApi(sourceCurrency: String): AppResult<CurrencyRateResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCurrencyRate(
                    accessKey = accessKey,
                    sourceCurrency = sourceCurrency.ifEmpty { "USD" })
                if (response.isSuccessful) {
                    response.body()?.let {
                        AppResult.Success(it)
                    } ?: run {
                        AppResult.Failure(Exception("Error parsing body!"))
                    }

                } else {
                    handleResponseError(response)
                }
            } catch (e: Exception) {
                AppResult.Failure(e)
            }

        }
    }


    private suspend fun getSupportedCurrenciesFromCache(): List<SupportedCurrencyEntity>? {
        return withContext(Dispatchers.IO) {
            supportedCurrenciesDao.findAll()
        }
    }

    private suspend fun getCurrencyRateFromCache(source: String): List<CurrencyRateDomainModel>? {
        return withContext(Dispatchers.IO) {
            currencyRateDao.getAllCurrencyRateWithCountryName(source)
        }
    }


    private fun needToSync(): Boolean {
        val currentTimestamp = System.currentTimeMillis()
        val savedTimestamp = pref.getLastSyncedTime()
        val diff = TimeUnit.MINUTES.toMillis(30)
        val out = currentTimestamp - savedTimestamp > diff

        return out
    }


}