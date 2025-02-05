

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myclothappnew.R
import com.example.myclothappnew.ui.component.BottomNavigationBar
import com.google.api.Context

// Data class for Product
data class Product(val imageRes: Int, val name: String, val price: String)

@Composable
fun ProductsPage(navController: NavHostController) {
    val products = listOf(
        Product(R.drawable.product1, "Denim pants", "$25.00"),
        Product(R.drawable.product2, "Mini skirt", "$30.00"),
        Product(R.drawable.product3, "White pants", "$45.00"),
        Product(R.drawable.product4, "Casual shirt", "$40.00"),
        Product(R.drawable.product5, "Blazer", "$55.00"),
        Product(R.drawable.product6, "Winter coat", "$60.00"),
        Product(R.drawable.product7, "Long dress", "$50.00"),
        Product(R.drawable.product8, "White top", "$70.00")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackgroundColor()) // Background theme applied
    ) {
        // Header image with overlay text
        HeaderImage(R.drawable.header_image)

        SearchBar()

        // Scrollable grid of products
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products) { product ->
                ProductItem(product, navController)
            }

        }
        BottomNavigationBar(
            currentScreen = "AvailableProducts",
            onNavigateToCart = { navController.navigate("cartPage") },
            onNavigateToProfile = { navController.navigate("profile") },
            onNavigateToHome = { navController.navigate("available_products") }
        )
    }
}
@Composable
fun ViewMoreSection(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp)
            .clickable { navController.navigate("more_products") }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.view_more_bg),
            contentDescription = "View More",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .alpha(0.8f),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "View More",
            color = appTextColor(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    // Search bar container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search Products", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)) }, // Text color from theme
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface, // Background theme applied
                    shape = MaterialTheme.shapes.medium
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),

            shape = MaterialTheme.shapes.medium
        )
    }
}

@Composable
fun HeaderImage(imageRes: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Header Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .alpha(0.8f),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "New Arrivals",
            color = appTextColor(), // Text color from theme
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProductItem(product: Product, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("view_product/${product.name}") },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
