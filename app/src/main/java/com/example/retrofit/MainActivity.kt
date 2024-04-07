package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.retrofit.ui.theme.RetrofitTheme
import com.example.retrofit.view.navigation.RootNavigation
import com.example.retrofit.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitTheme {
                val authViewModel: AuthViewModel = viewModel()
                RootNavigation(navController = rememberNavController(), authViewModel)
            }
        }
    }
}
