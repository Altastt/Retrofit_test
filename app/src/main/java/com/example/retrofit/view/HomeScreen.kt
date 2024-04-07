package com.example.retrofit.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofit.view.navigation.RootNavigation
import com.example.retrofit.viewmodel.AuthViewModel

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(RootNavigation.GETPRODUCT) },
            modifier = Modifier.width(150.dp)
                .padding(bottom = 20.dp)
        ) {
            Text("Get product")
        }

        Button(
            onClick = { navController.navigate(RootNavigation.GETPRODUCTLIST) },
            modifier = Modifier.width(150.dp)
                .padding(bottom = 20.dp)
        ) {
            Text("Product list")
        }

        Button(
            onClick = { navController.navigate(RootNavigation.POSTAUTH) },
            modifier = Modifier.width(150.dp)
                .padding(bottom = 20.dp)
        ) {
            Text("Login")
        }

        Button(
            onClick = { navController.navigate(RootNavigation.WEATHER) },
            modifier = Modifier.width(150.dp)
        ) {
            Text("Weather")
        }
    }
}