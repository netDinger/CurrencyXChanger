package com.ppk.currencyxchanger.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "supported_currency")
@Parcelize
class SupportedCurrencyEntity(@PrimaryKey val currencyCode: String, val countryName: String?) :
    Parcelable {

    override fun toString(): String {
        return currencyCode
    }

}

