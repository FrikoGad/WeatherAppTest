package com.example.weatherapp.data.network.response

import com.example.weatherapp.data.db.entity.current.Current
import com.example.weatherapp.data.db.entity.current.Location

data class WeatherResponse(
    val current: Current,
    val location: Location
)