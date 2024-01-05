package com.ppk.currencyxchanger.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ppk.currencyxchanger.presentation.model.SupportedCurrencyUiModel
import com.ppk.currencyxchanger.presentation.view.CountryCell


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSpinner(
    supportedCurrencies: List<SupportedCurrencyUiModel>,
    modifier: Modifier,
    onSelected: (SupportedCurrencyUiModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("USD") }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = { selectedValue = it },
//            modifier = Modifier
//
//                .onGloballyPositioned { coordinates ->
//                    //This value is used to assign to the DropDown the same width
//                    textfieldSize = coordinates.size.toSize()
//                },
            label = { Text("Currency") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            ) {

            supportedCurrencies.forEach { c ->
                DropdownMenuItem(text = {
                    CountryCell(countryCode = c.currencyCode, countryName = c.countryName)
                }, onClick = {
                    selectedValue = c.currencyCode
                    expanded = false
                    onSelected(c)

                })
            }
        }
    }

}
