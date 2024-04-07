package com.example.retrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.model.Product
import com.example.retrofit.model.Products
import com.example.retrofit.model.api.ProductApi
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _productTitle = MutableLiveData<String>()
    val productTitle: LiveData<String> = _productTitle

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    private val _query = MutableLiveData("")
    val query: LiveData<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }
    suspend fun getProduct(productApi: ProductApi, token: String) {
        viewModelScope.launch {
            try {
                val product = productApi.getProductById(token, 3)
                _productTitle.value = product.title
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }

        }
    }
    suspend fun getProductList(productApi: ProductApi, token: String) {
        viewModelScope.launch {
            try {
                val productList = productApi.getAllProducts(token).products
                _productList.postValue(productList) // Здесь изменяем значение
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }
        }
    }

    suspend fun getProductByName(productApi: ProductApi,token: String, name: String) {
        viewModelScope.launch {
            try {
                val productList = productApi.getProductsByNameAuth(token, name)
                _productList.postValue(productList.products)
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }
        }
    }

}