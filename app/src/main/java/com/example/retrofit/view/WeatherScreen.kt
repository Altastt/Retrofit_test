package com.example.retrofit.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofit.model.api.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun WeatherScreen() {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var temp by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Data: $date", modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Temp: $temp", modifier = Modifier.padding(bottom = 50.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val api = retrofit.create(WeatherApi::class.java)
                    val model = api.getWeatherData(
                        "a64d5ea242624661884180930240704",
                        "Moscow",
                        "3",
                        "no",
                        "no"
                    )
                    temp = model.current.temp_c.toString()
                    date = model.location.localtime
                }
            }
        ) {
            Text(text = "Get Weather")
        }
    }
}
