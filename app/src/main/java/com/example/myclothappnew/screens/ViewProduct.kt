package com.example.myclothappnew.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import appBackgroundColor
import appTextColor
import buttonColor
import com.example.myclothappnew.R
import com.example.myclothappnew.viewModels.CartViewModel
import com.example.myclothappnew.models.CartItem
import com.example.myclothappnew.ui.component.BottomNavigationBar

// Data class for Product
data class Product(
    val imageRes: Int,
    val name: String,
    val price: String,
    val description: String,
    val customerReview: String
)

@Composable
fun ViewProductPage(
    navController: NavHostController,
    productName: String?,
    cartViewModel: CartViewModel = viewModel()
) {
    // Mock product data based on productName
    val product = remember {
        when (productName) {
            "Denim pants" -> Product(
                R.drawable.product1,
                "Denim pants",
                "$25.00",
                "These stylish black pants are perfect for formal and casual settings.",
                "High-quality material. Fits perfectly!"
            )

            "Mini skirt" -> Product(
                R.drawable.product2,
                "Mini skirt",
                "$30.00",
                "Trendy mini skirt for a chic and modern look.",
                "Looks amazing! I get compliments every time I wear it."
            )

            "White pants" -> Product(
                R.drawable.product3,
                "White pants",
                "$45.00",
                "Elegant white pants for a timeless fashion statement.",
                "Very comfortable and easy to style."
            )

            "Casual shirt" -> Product(
                R.drawable.product4,
                "Casual shirt",
                "$40.00",
                "Classic button-down shirt for any occasion.",
                "Perfect fit. Great quality fabric."
            )

            "Blazer" -> Product(
                R.drawable.product5,
                "Blazer",
                "$55.00",
                "Sophisticated blazer for a sharp and professional look.",
                "Excellent tailoring. Highly recommend!"
            )

            "Winter coat" -> Product(
                R.drawable.product6,
                "Winter coat",
                "$60.00",
                "A versatile black top for any wardrobe.",
                "So comfortable and stylish!"
            )

            "Long dress" -> Product(
                R.drawable.product7,
                "Long dress",
                "$50.00",
                "A beautiful long dress for formal events or casual outings.",
                "I love the flowy fabric and elegant design."
            )

            "White top" -> Product(
                R.drawable.product8,
                "White top",
                "$70.00",
                "A crisp white top that pairs well with anything.",
                "Very versatile and easy to maintain."
            )

            else -> Product(
                R.drawable.product1,
                "Unknown Product",
                "$0.00",
                "Description not available.",
                "No reviews yet."
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackgroundColor())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Add padding to ensure space for the bottom navigation bar
        ) {
            // Red Bar with Back Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable { navController.navigate("available_products") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card for product details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background) // Dynamically set background color
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Product Name and Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                            ),
                        )

                        Text(
                            text = product.price,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Image
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Description
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Available Colors and Sizes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Available Colors
                        Column {
                            Text(
                                text = "Available Colors:",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val colors = listOf(Color.Red, Color.Blue, Color.Green)
                                colors.forEach { color ->
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(color, shape = CircleShape)
                                    )
                                }
                            }
                        }

                        // Available Sizes
                        Column {
                            Text(
                                text = "Available Sizes:",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val sizes = listOf("S", "M", "L", "XL")
                                sizes.forEach { size ->
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .background(
                                                MaterialTheme.colorScheme.secondary,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = size,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 14.sp
                                            ),
                                            color = MaterialTheme.colorScheme.onBackground // Dynamically set text color
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val newItem = CartItem(product.imageRes, product.name, product.price)
                        cartViewModel.addItemToCart(newItem)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor())
                ) {  Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Add to Cart",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            color = appTextColor()
                        )
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { navController.navigate("order") },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor())
                ) {
                    Text(
                        text = "Buy Now",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            color = appTextColor()
                        )
                    )
                }
            }



        }
    }
}
