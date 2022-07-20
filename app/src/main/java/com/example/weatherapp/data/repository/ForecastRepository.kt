package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.entity.current.Location
import com.example.weatherapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherapp.data.network.response.WeatherDaysResponse
import retrofit2.Response

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<Location>
    suspend fun getDaysWeather(): Response<WeatherDaysResponse>
}