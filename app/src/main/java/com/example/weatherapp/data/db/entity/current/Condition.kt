package com.example.weatherapp.data.db.entity.current

import androidx.room.ColumnInfo

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)