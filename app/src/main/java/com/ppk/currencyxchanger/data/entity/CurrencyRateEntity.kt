package com.ppk.currencyxchanger.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "currency_rate")
@Parcelize
open class CurrencyRateEntity(
    @PrimaryKey val currencyCode: String,
    val source: String,
    val rate: Double
) : Parcelable