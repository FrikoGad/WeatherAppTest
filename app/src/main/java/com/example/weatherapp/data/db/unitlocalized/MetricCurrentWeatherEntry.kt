package com.example.weatherapp.data.db.unitlocalized

import androidx.room.ColumnInfo

class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "temp_c")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "wind_dir")
    override val windDirection: String,
    @ColumnInfo(name = "precip_mm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslike_c")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "vis_km")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "wind_kph")
    override val windSpeed: Double,
    @ColumnInfo(name = "last_updated")
    override val updateTime: String
) : UnitSpecificCurrentWeatherEntry