package com.example.retrofit.model.api

import com.example.retrofit.model.Product
import com.example.retrofit.model.Products
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @Headers("Content-Type: application/json")
    @GET("auth/products/{id}")
    suspend fun getProductById(@Header("Authorization") token: String, @Path("id") id: Int) : Product

    @Headers("Content-Type: application/json")
    @GET("auth/products")
    suspend fun getAllProducts(@Header("Authorization") token: String) : Products

    @Headers("Content-Type: application/json")
    @GET("auth/products/search")
    suspend fun getProductsByNameAuth(@Header("Authorization") token: String, @Query("q") name: String) : Products

}