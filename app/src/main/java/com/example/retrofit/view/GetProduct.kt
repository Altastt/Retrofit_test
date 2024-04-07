package com.example.retrofit.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofit.model.api.ProductApi
import com.example.retrofit.viewmodel.AuthViewModel
import com.example.retrofit.viewmodel.ProductViewModel
import com.example.retrofit.viewmodel.RetrofitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GetProduct(
    authViewModel: AuthViewModel,
    retrofitViewModel: RetrofitViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel()
) {

    val productApi = retrofitViewModel.retrofit.create(ProductApi::class.java)
    val tokenState = remember { mutableStateOf("") }
    val productTitleState = remember { mutableStateOf("") }

    // Получение текста из ViewModel
    DisposableEffect(productViewModel) {
        val observer = Observer<String> { productTitle ->
            productTitleState.value = productTitle
        }
        productViewModel.productTitle.observeForever(observer)
        val observerToken = Observer<String> { token ->
            tokenState.value = token
        }
        authViewModel.tokenState.observeForever(observerToken)
        onDispose {
            productViewModel.productTitle.removeObserver(observer)
            authViewModel.tokenState.observeForever(observerToken)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = productTitleState.value,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    productViewModel.getProduct(productApi, tokenState.value)
                }
            },
        ) {
            Text("Get Product")
        }
    }
}