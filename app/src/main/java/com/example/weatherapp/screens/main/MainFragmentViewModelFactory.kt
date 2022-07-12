package com.example.weatherapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.repository.ForecastRepository

class MainFragmentViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(forecastRepository, unitProvider) as T
    }
}