package com.example.retrofit.model

data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Float,
    val rating: Float,
    val stock:  Float,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)