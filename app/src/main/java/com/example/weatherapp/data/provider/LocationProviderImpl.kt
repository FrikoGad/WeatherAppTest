package com.example.weatherapp.data.provider

import com.example.weatherapp.data.db.entity.current.Location

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean {
        //dev
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        //dev
        return "St. Petersburg"
    }
}