package com.ppk.currencyxchanger.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ppk.currencyxchanger.presentation.components.DropdownSpinner
import com.ppk.currencyxchanger.presentation.components.ProgressDialog
import com.ppk.currencyxchanger.presentation.model.CurrencyRateUiModel
import com.ppk.currencyxchanger.presentation.model.SupportedCurrencyUiModel
import com.ppk.currencyxchanger.presentation.viewmodel.HomeViewModel
import com.ppk.currencyxchanger.util.ViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var supportedCurrencies = remember { mutableStateListOf<SupportedCurrencyUiModel>() }
    var currencyRates = remember { mutableStateListOf<CurrencyRateUiModel>() }
    var amount by remember { mutableStateOf("") }
    var sourceCurrencyCode by remember { mutableStateOf("USD") }

    var showAlert by remember { mutableStateOf(false) }
    var alertTitle by remember { mutableStateOf("Error") }
    var alertMsg by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }


    @Composable
    fun showAlert() {
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(text = alertTitle) },
                text = { Text(text = alertMsg) },
                dismissButton = {
                },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(text = "OK")
                    }
                },
                modifier = Modifier.clip(RoundedCornerShape(size = 20.dp))
            )
        }
        if (showLoading) {
            ProgressDialog {
                showLoading = false
            }
        }
    }

    @Composable
    fun onCreate() {
        showAlert()
        LaunchedEffect(key1 = Unit, block = {
            viewModel.getAllSupportedCurrencies()
            viewModel.getAllCurrencyRates()
            scope.launch {
                viewModel.supportedCurrencyState.collect {
                    when (it) {
                        is ViewState.Error -> {
                            showLoading = false
                            alertTitle = "Error"
                            alertMsg = it.error
                            showAlert = true
                        }

                        ViewState.Loading -> {
                            showLoading = true
                        }

                        ViewState.NoData -> {
                            showLoading = false
                        }

                        is ViewState.Success -> {
                            showLoading = false
                            supportedCurrencies.addAll(it.data)
                        }
                    }
                }
            }
            scope.launch {
                viewModel.currencyRateState.collect {
                    when (it) {
                        is ViewState.Error -> {
                            showLoading = false
                            alertTitle = "Error"
                            alertMsg = it.error
                            showAlert = true
                        }

                        ViewState.Loading -> {
                            showLoading = true
                        }

                        ViewState.NoData -> {
                            showLoading = false
                        }

                        is ViewState.Success -> {
                            showLoading = false
                            currencyRates.clear()
                            currencyRates.addAll(it.data)
                        }
                    }
                }
            }
        })
    }
    onCreate()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Currency XChanger") })

    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            showAlert()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(value = amount, onValueChange = {
                    amount = it
                }, label = { Text("Amount") }, modifier = Modifier.weight(0.4f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(onGo = {
                        val changeAmount = amount.toDoubleOrNull()
                        if (changeAmount != null) {
                            viewModel.getAllCurrencyRates(
                                amount = amount.toDouble(),
                                sourceCurrencyCode
                            )
                        } else {
                            Toast.makeText(context, "Please enter valid amount", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                )
                Spacer(modifier = Modifier.weight(0.05f))

                DropdownSpinner(supportedCurrencies, modifier = Modifier.weight(0.4f)) {
                    sourceCurrencyCode = it.currencyCode
                }
                Spacer(modifier = Modifier.weight(0.05f))

                Button(onClick = {
                    val changeAmount = amount.toDoubleOrNull()
                    if (changeAmount != null) {
                        viewModel.getAllCurrencyRates(
                            amount = amount.toDouble(),
                            sourceCurrencyCode
                        )
                    } else {
                        Toast.makeText(context, "Please enter valid amount", Toast.LENGTH_SHORT)
                            .show()
                    }

                }) {
                    Text("Go!")
                }
                Spacer(modifier = Modifier.weight(0.1f))
            }


            LazyColumn {
                items(currencyRates.size) { index ->
                    RateCell(currencyRates[index])
                }
            }

        }
    }
}
