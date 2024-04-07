package com.example.retrofit.model

data class ForecastModel(
    val forecastday: List<ForecastDayModel>
)

data class ForecastDayModel(
    val date: String,
    val day: DayModel,
    val hour: List<HourModel>,
)

data class DayModel(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val condition: ConditionModel,
)
data class HourModel(
    val time: String,
    val temp_c: Float,
    val condition: ConditionModel
)