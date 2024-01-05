package com.ppk.currencyxchanger.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun CountryCell(countryCode: String, countryName: String) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = countryCode,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = countryName, style = MaterialTheme.typography.bodyMedium)
    }
}