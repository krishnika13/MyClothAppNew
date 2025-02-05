package com.example.myclothappnew.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myclothappnew.R
import kotlinx.coroutines.delay

@Composable
fun LandingPage(navController: NavHostController) {
    // State variables to control the visibility of elements
    var textVisible by remember { mutableStateOf(false) }
    var buttonsVisible by remember { mutableStateOf(false) }

    // Trigger animation visibility after a delay
    LaunchedEffect(Unit) {
        delay(300) // Delay before the MUSE text appears
        textVisible = true
        delay(500) // Delay before the buttons appear
        buttonsVisible = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background with gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                )
        ) {
            Image(
                painter = painterResource(R.drawable.landing),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f)
            )

            AnimatedVisibility(
                visible = textVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000))
            ) {
                Text(
                    text = "MUSE",
                    color = Color.White,
                    fontSize = 110.sp,
                    fontFamily = FontFamily(Font(R.font.cinzelregular)),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(top = 16.dp)
                )
            }

        }

        // Animated Buttons at the bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(23.dp)
        ) {
            AnimatedVisibility(
                visible = buttonsVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000))
            ) {
                Column {
                    Button(
                        onClick = { navController.navigate("login") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .alpha(0.5f)
                    ) {
                        Text(
                            text = "Login",
                            color = Color.Black,
                            fontSize = 21.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("register") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .alpha(0.5f)
                    ) {
                        Text(
                            text = "Register",
                            color = Color.Black,
                            fontSize = 21.sp
                        )
                    }

                }
            }
        }
    }
}
