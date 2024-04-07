package com.example.retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.model.AuthRequest
import com.example.retrofit.model.api.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    private val _firstNameState = MutableLiveData<String>()
    val firstNameState: LiveData<String> = _firstNameState
    private val _lastNameState = MutableLiveData<String>()
    val lastNameState: LiveData<String> = _lastNameState
    private val _urlState = MutableLiveData<String>()
    val urlState: LiveData<String> = _urlState
    private val _emailState = MutableLiveData<String>()
    val emailState: LiveData<String> = _emailState
    private val _passwordState = MutableLiveData<String>()
    val passwordState: LiveData<String> = _passwordState

    private val _tokenState = MutableLiveData<String>()
     val tokenState: LiveData<String> = _tokenState

    fun setToken(token: String) {
        _tokenState.value = token
    }

    suspend fun authenticate(email: String, password: String, authApi: AuthApi) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = authApi.authRequest(AuthRequest(email, password))
                withContext(Dispatchers.Main) {
                    _urlState.value = user.image
                    _firstNameState.value = user.firstName
                    _lastNameState.value = user.lastName
                    // Установка токена после успешной аутентификации
                    setToken(user.token)
                }
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }
        }
    }


}