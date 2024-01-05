package com.ppk.currencyxchanger.data.service


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PrefService(context: Context) {
    private val applicationContext = context.applicationContext
    private val editor: SharedPreferences.Editor
    private val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
        "CurrencyPref",
        MODE_PRIVATE
    )

    init {
        editor = sharedPreferences.edit()
    }

    fun saveLastSyncedTime(lastSyncedTimeStamp: Long) {
        editor.putLong(LAST_SYNCED_TIME, lastSyncedTimeStamp)
        editor.commit()
    }

    fun getLastSyncedTime(): Long {
        return sharedPreferences.getLong(LAST_SYNCED_TIME, -1)
    }


    companion object {
        const val LAST_SYNCED_TIME = "last_synced"
    }
}