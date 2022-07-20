package com.example.weatherapp.screens.days

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.response.WeatherDaysResponse
import com.example.weatherapp.data.repository.ForecastRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DaysFragmentViewModel(
    private val forecastRepository: ForecastRepository
    ) : ViewModel() {

    val daysWeather: MutableLiveData<Response<WeatherDaysResponse>> = MutableLiveData()

    fun getDaysWeatherRetrofit() {
        viewModelScope.launch {
            try {
                daysWeather.value = forecastRepository.getDaysWeather()
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }
}