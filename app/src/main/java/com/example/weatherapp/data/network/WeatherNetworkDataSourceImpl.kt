package com.example.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.network.response.WeatherResponse
import com.example.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiWeatherApiService : ApiService
) : WeatherNetworkDataSource {
    private val _downloaderCurrentWeather = MutableLiveData<WeatherResponse>()
    override val downloaderCurrentWeather: LiveData<WeatherResponse>
        get() = _downloaderCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, aqi: String) {
        try {
            val fetchedCurrentWeather = apiWeatherApiService
                .getCurrentWeatherAsync(location, aqi)
                .await()
            _downloaderCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}