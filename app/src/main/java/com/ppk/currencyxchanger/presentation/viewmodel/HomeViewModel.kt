package com.ppk.currencyxchanger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppk.currencyxchanger.domain.useCase.CurrencyUseCase
import com.ppk.currencyxchanger.presentation.model.CurrencyRateUiModel
import com.ppk.currencyxchanger.presentation.model.SupportedCurrencyUiModel
import com.ppk.currencyxchanger.presentation.uiMapper.toUiModel
import com.ppk.currencyxchanger.util.AppResult
import com.ppk.currencyxchanger.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: CurrencyUseCase
) : ViewModel() {

    private val _supportedCurrencyState =
        MutableStateFlow<ViewState<List<SupportedCurrencyUiModel>>>(ViewState.NoData)
    val supportedCurrencyState: StateFlow<ViewState<List<SupportedCurrencyUiModel>>> =
        _supportedCurrencyState

    private val _currencyRateState =
        MutableStateFlow<ViewState<List<CurrencyRateUiModel>>>(ViewState.NoData)
    val currencyRateState: StateFlow<ViewState<List<CurrencyRateUiModel>>> = _currencyRateState


    fun getAllSupportedCurrencies() {
        _supportedCurrencyState.value = ViewState.Loading
        viewModelScope.launch {

            when (val result = useCase.getSupportedCurrencies()) {
                is AppResult.Success -> {
                    result.data?.let { data ->
                        _supportedCurrencyState.value =
                            ViewState.Success(data.map { it.toUiModel() })
                    } ?: run {
                        _supportedCurrencyState.value = ViewState.Error("no supported currencies")
                    }

                }

                is AppResult.Failure -> _supportedCurrencyState.value =
                    ViewState.Error(result.error.message.toString())
            }
        }
    }


    fun getAllCurrencyRates(amount: Double = 0.0, sourceCurrency: String = "") {
        _currencyRateState.value = ViewState.Loading
        viewModelScope.launch {
            when (val result = useCase.getCurrencyRate(sourceCurrency)) {
                is AppResult.Success -> {
                    if (amount == 0.0) {
                        _currencyRateState.value = ViewState.Success(result.data.map {
                            it.toUiModel()
                        })
                    } else {
                        _currencyRateState.value = ViewState.Success(result.data.map {
                            CurrencyRateUiModel(
                                currencyCode = it.currencyCode, countryName = it.countryName,
                                rate = it.rate * amount
                            )
                        })
                    }
                }

                is AppResult.Failure -> _currencyRateState.value =
                    ViewState.Error(result.error.message.toString())
            }
        }
    }


}