package com.example.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.weatherapp.internal.UnitSystem
import com.example.weatherapp.utils.UNIT_SYSTEM

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}