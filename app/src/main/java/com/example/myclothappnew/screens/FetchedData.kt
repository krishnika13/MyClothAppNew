package com.example.myclothappnew.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.myclothappnew.viewModels.ProductViewModel
import com.example.myclothappnew.models.ProductResponse

@Composable
fun FetchedData(navController: NavHostController, productViewModel: ProductViewModel = viewModel()) {
    val products by productViewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "More Products",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier.height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
