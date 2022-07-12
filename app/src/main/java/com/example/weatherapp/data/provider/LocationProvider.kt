package com.example.weatherapp.data.provider

import com.example.weatherapp.data.db.entity.current.Location

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
    suspend fun getPreferredLocationString(): String
}