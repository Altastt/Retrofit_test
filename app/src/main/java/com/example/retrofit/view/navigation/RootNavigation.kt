package com.example.retrofit.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofit.view.*
import com.example.retrofit.viewmodel.AuthViewModel

@Composable
fun RootNavigation(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController, startDestination = RootNavigation.HOME) {
        composable(RootNavigation.HOME) {
            HomeScreen(navController = navController, authViewModel)
        }
        composable(RootNavigation.POSTAUTH) {
            Authentication(authViewModel)
        }
        composable(RootNavigation.GETPRODUCT) {
            GetProduct(authViewModel)
        }
        composable(RootNavigation.GETPRODUCTLIST) {
            ProductList(authViewModel)
        }
        composable(RootNavigation.WEATHER) {
            WeatherScreen()
        }
    }
}

object RootNavigation {
    const val HOME = "home"
    const val POSTAUTH = "post_auth"
    const val GETPRODUCT = "get_product"
    const val GETPRODUCTLIST = "get_product_list"
    const val WEATHER = "weather"
}