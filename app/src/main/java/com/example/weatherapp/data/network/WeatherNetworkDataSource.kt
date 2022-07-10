package com.example.weatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.network.response.WeatherResponse

interface WeatherNetworkDataSource {

    val downloaderCurrentWeather: LiveData<WeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        aqi: String
    )
}