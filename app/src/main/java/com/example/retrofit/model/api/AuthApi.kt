package com.example.retrofit.model.api

import com.example.retrofit.model.AuthRequest
import com.example.retrofit.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun authRequest(@Body authRequest: AuthRequest): User // можно сделать Response<User> чтобы приложение не закрывалось с ошибкой (user будет равен body) передача по response = api.authApi()
}