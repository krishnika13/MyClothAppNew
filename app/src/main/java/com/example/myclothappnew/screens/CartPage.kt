package com.example.myclothappnew.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myclothappnew.viewModels.CartViewModel
import com.example.myclothappnew.models.CartItem
import com.example.myclothappnew.ui.component.BottomNavigationBar

@Composable
fun CartPage(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems = cartViewModel.cartItems.value

    // Calculate the total amount dynamically
    val totalAmount = cartItems.sumOf { it.price.toDoubleOrNull() ?: 0.0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Replace with appBackgroundColor if defined
    ) {
        // Page Title
        Text(
            text = "My Cart",
            color = MaterialTheme.colorScheme.onBackground, // Replace with appTextColor if defined
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(start = 32.dp, top = 90.dp)
        )

        // Cart Items List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp, horizontal = 16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Text(
                    text = "Your cart is empty.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                cartItems.forEach { item ->
                    CartItemRow(
                        cartItem = item,
                        onRemoveClick = { cartViewModel.removeItemFromCart(item) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Total Amount and Buy Now Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Total Amount
                Text(
                    text = "Total: $${"%.2f".format(totalAmount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Buy Now Button
                Button(
                    onClick = { navController.navigate("order") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Buy Now", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            currentScreen = "Cart",
            onNavigateToCart = { navController.navigate("cartPage") },
            onNavigateToProfile = { navController.navigate("profile") },
            onNavigateToHome = { navController.navigate("available_products") }
        )
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        Image(
            painter = painterResource(id = cartItem.imageRes),
            contentDescription = cartItem.name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartItem.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Price: ${cartItem.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Remove Button
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove Item",
                tint = MaterialTheme.colorScheme.onError
            )
        }
    }
}
