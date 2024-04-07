package com.example.retrofit.view

/*
* В данном случае пользователь не запоминается,
* либо запоминать пользователя в БД,
* либо дело в том, что окно входа/регистрации находится в той же навигационной ветке, что и те окна, в которые я перехожу
*   ВСЕ РАБОТАЕТ
* */

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofit.R
import com.example.retrofit.model.Product
import com.example.retrofit.model.api.ProductApi
import com.example.retrofit.viewmodel.AuthViewModel
import com.example.retrofit.viewmodel.ProductViewModel
import com.example.retrofit.viewmodel.RetrofitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(
    authViewModel: AuthViewModel,
    retrofitViewModel: RetrofitViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel(),
) {

    val productApi = retrofitViewModel.retrofit.create(ProductApi::class.java)
    val productListState = remember { mutableStateOf<List<Product>?>(null) }
    val queryState = remember { mutableStateOf("") }
    val activeState = remember { mutableStateOf(false) }
    val tokenState = remember { mutableStateOf("") }
    val items = remember {
        mutableListOf(
            "Honor"
        )
    }

    LaunchedEffect(productViewModel) {
        productViewModel.getProductList(productApi, tokenState.value)
    }
// Получение значений из ViewModel
    DisposableEffect(productViewModel) {
        val observerList = Observer<List<Product>> { productList ->
            productListState.value = productList
        }
        val observerToken = Observer<String> { token ->
            tokenState.value = token
        }
        val observerQuery = Observer<String> { query ->
            queryState.value = query
        }
        productViewModel.productList.observeForever(observerList)
        productViewModel.query.observeForever(observerQuery)
        authViewModel.tokenState.observeForever(observerToken)

        onDispose {
            productViewModel.productList.removeObserver(observerList)
            productViewModel.query.removeObserver(observerQuery)
            authViewModel.tokenState.removeObserver(observerToken)
        }
    }
    productListState.value?.let { productList ->
        SearchBar(
            query = queryState.value,
            onQueryChange = {
                activeState.value = true
                productViewModel.updateQuery(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            onSearch = {
                items.add(queryState.value)
                CoroutineScope(Dispatchers.IO).launch {
                    productViewModel.getProductByName(productApi, token = tokenState.value, queryState.value)
                }
                activeState.value = false

            },
            active = activeState.value,
            onActiveChange = {
                activeState.value = it
            },
            placeholder = { Text("Search") },
            trailingIcon = {
                if (activeState.value) {
                    Icon(painterResource(R.drawable.close), "Close",
                        modifier = Modifier.size(30.dp).clickable {
                            if (queryState.value.isNotEmpty()) {
                                queryState.value = ""
                            } else {
                                activeState.value = false
                            }
                        })
                }
            }
        ) {
            items.forEach {
                Row(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.history), "History",
                        modifier = Modifier.padding(end = 10.dp).size(30.dp)
                    )
                    Text(text = it)
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp)
        ) {
            items(productList) { product ->
                ProductItem(item = product)
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun ProductItem(item: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = item.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        Text(text = item.description)
    }
}