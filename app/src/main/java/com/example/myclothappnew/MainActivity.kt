package com.example.myclothappnew

import ProductsPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.myclothappnew.viewModels.CartViewModel
import com.example.myclothappnew.viewModels.OrderViewModel
import com.example.myclothappnew.screens.*
import com.example.myclothapp.LoginPage
import com.example.myclothappnew.ui.ContactAccessScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                MyAppNavHost(navController)
            }
        }
    }
}

@Composable
fun MyAppNavHost(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()

    NavHost(navController, startDestination = "landing") {
        composable("landing") { LandingPage(navController) }
        composable("login") { LoginPage(navController) }
        composable("register") { RegisterPage(navController) }
        composable("available_products") { ProductsPage(navController) }
        composable("view_product/{productName}") { backStackEntry ->
            ViewProductPage(
                navController,
                backStackEntry.arguments?.getString("productName"),
                cartViewModel
            )
        }
        composable("cartPage") { CartPage(navController, cartViewModel) }
        composable("profile") { UserProfilePage(navController) }
        composable("fetched_data") { FetchedData(navController) }
        composable("order") { OrderScreen(orderViewModel) }
        composable("contacts") { ContactAccessScreen() }  // ✅ Properly placed here
    }
}
