package com.example.weatherapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.internal.UnitSystem
import com.example.weatherapp.internal.lazyDeferred

class MainFragmentViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}