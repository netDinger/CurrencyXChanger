package com.ppk.currencyxchanger.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ppk.currencyxchanger.presentation.model.CurrencyRateUiModel
import java.math.BigDecimal
import java.math.RoundingMode

//Actually BigDecimal round up logic should sit in viewmodel or in uimapper.
//but for this kind of simple app with build time restriction, i sit like this
//TODO: broken logic
@Composable
fun RateCell(currencyRateUiModel: CurrencyRateUiModel) {

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
        Row() {
            Text(
                text = currencyRateUiModel.currencyCode,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = BigDecimal(currencyRateUiModel.rate).setScale(3, RoundingMode.HALF_UP)
                    .toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(currencyRateUiModel.countryName, style = MaterialTheme.typography.bodyMedium)
    }
}