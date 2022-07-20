package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.response.WeatherDaysResponse
import com.example.weatherapp.data.network.response.WeatherResponse
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    fun getDaysWeatherAsync(
        @Query("q") location: String,
        @Query("days") days: String = "5",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ) : Response<WeatherDaysResponse>

    @GET("current.json")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no",
    ) : Deferred<WeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request =chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}