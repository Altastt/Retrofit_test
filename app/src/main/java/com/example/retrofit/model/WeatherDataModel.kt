package com.example.retrofit.model

data class WeatherDataModel(
    val location: LocalModel,
    val current: CurrentModel,
    val forecast: ForecastModel,
)

data class LocalModel(
    val name: String,
    val localtime: String
)

data class CurrentModel(
    val last_updated: String,
    val temp_c: Float,
    val condition: ConditionModel
)

data class ConditionModel(
    val text: String,
    val icon: String,
)