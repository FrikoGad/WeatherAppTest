package com.example.weatherapp.screens.days

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.repository.ForecastRepository

class DaysFragmentViewModelFactory(
    private val forecastRepository: ForecastRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DaysFragmentViewModel(forecastRepository) as T
    }
}