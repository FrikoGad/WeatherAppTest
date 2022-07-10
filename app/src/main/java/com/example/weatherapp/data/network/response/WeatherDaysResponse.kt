package com.example.weatherapp.data.network.response

import com.example.weatherapp.data.db.entity.others.Current
import com.example.weatherapp.data.db.entity.others.Forecast
import com.example.weatherapp.data.db.entity.others.Location

data class WeatherDaysResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)