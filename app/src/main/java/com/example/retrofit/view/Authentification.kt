package com.example.retrofit.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.retrofit.R
import com.example.retrofit.model.api.AuthApi
import com.example.retrofit.viewmodel.AuthViewModel
import com.example.retrofit.viewmodel.RetrofitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Authentication(
    authViewModel: AuthViewModel,
    retrofitViewModel: RetrofitViewModel = viewModel()
) {
    val authApi = retrofitViewModel.retrofit.create(AuthApi::class.java)
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val urlState = remember { mutableStateOf("") }
    val showPasswordState = remember { mutableStateOf(false) }


    DisposableEffect(authViewModel) {
        val observerUrlState = Observer<String> { _urlState ->
            urlState.value = _urlState
        }
        authViewModel.urlState.observeForever(observerUrlState)

        val observerFirstNameState = Observer<String> { _firstNameState ->
            firstNameState.value = _firstNameState
        }
        authViewModel.firstNameState.observeForever(observerFirstNameState)

        val observerLastNameState = Observer<String> { _lastNameState ->
            lastNameState.value = _lastNameState
        }
        authViewModel.lastNameState.observeForever(observerLastNameState)

        val observerEmailState = Observer<String> { _emailState ->
            emailState.value = _emailState
        }
        authViewModel.emailState.observeForever(observerEmailState)

        val observerPasswordState = Observer<String> { _passwordState ->
            passwordState.value = _passwordState
        }
        authViewModel.passwordState.observeForever(observerPasswordState)
        onDispose {
            authViewModel.urlState.removeObserver(observerUrlState)
            authViewModel.firstNameState.removeObserver(observerFirstNameState)
            authViewModel.lastNameState.removeObserver(observerLastNameState)
            authViewModel.lastNameState.removeObserver(observerEmailState)
            authViewModel.lastNameState.removeObserver(observerPasswordState)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = urlState.value,
            contentDescription = null,
            modifier = Modifier.height(200.dp).padding(bottom = 50.dp, top = 40.dp)
        )

        Text(
            text = firstNameState.value,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = lastNameState.value,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        TextField(
            value = emailState.value,
            onValueChange = { it: String -> emailState.value = it },
            placeholder = { Text("Email") },
            modifier = Modifier.padding(bottom = 20.dp),
        )
        TextField(
            value = passwordState.value,
            onValueChange = { it: String -> passwordState.value = it },
            placeholder = { Text("Password") },
            visualTransformation = if (showPasswordState.value) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.padding(bottom = 20.dp),
            trailingIcon = {
                IconButton(onClick = { showPasswordState.value = !showPasswordState.value }) {
                    Icon(
                        painter = if (showPasswordState.value) painterResource(R.drawable.show_pass) else painterResource(
                            R.drawable.hide_pass
                        ),
                        contentDescription = if (showPasswordState.value) "Hide Password" else "Show Password"
                    )
                }
            }
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    authViewModel.authenticate(emailState.value, passwordState.value, authApi)
                }
            },
            modifier = Modifier.width(100.dp)
        ) {
            Text("Sign In")
        }
    }

}