package com.example.myclothapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import appBackgroundColor
import appTextColor
import buttonColor
import com.example.myclothappnew.viewModels.LoginViewModel
import com.example.myclothappnew.data.FirebaseAuthService
import kotlinx.coroutines.launch


@Composable
fun LoginPage(navController: NavHostController) {
    // Accessing the ViewModel
    val loginViewModel: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7C1204)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "LOGIN",
            style = TextStyle(color = Color.White, fontSize = 60.sp),
            modifier = Modifier.padding(top = 50.dp)
        )
        Text(
            text = "Login to discover a whole new world of fashion.",
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 18.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .clip(RoundedCornerShape(topStart = 80.dp))
                .background(appBackgroundColor())
                .padding(top = 40.dp)
        ) {
            LoginForm(navController, loginViewModel)
        }
    }
}

@Composable
fun LoginForm(navController: NavHostController, loginViewModel: LoginViewModel) {
    val username = loginViewModel.username.value
    val password = loginViewModel.password.value
    val isLoading = loginViewModel.isLoading.value
    val errorMessage = loginViewModel.errorMessage.value

    val firebaseAuthService = FirebaseAuthService()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Username / Email", fontSize = 27.sp, color = appTextColor())
        TextField(
            value = username,
            onValueChange = { loginViewModel.username.value = it },
            label = { Text("Username or Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp)
        )

        Text(text = "Password", fontSize = 27.sp, color = appTextColor())
        TextField(
            value = password,
            onValueChange = { loginViewModel.password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 45.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (loginViewModel.isLoginDataValid()) {
                        loginViewModel.isLoading.value = true
                        val result = firebaseAuthService.loginUser(username, password)
                        loginViewModel.isLoading.value = false
                        if (result != null) {
                            navController.navigate("available_products") // Corrected path
                        } else {
                            loginViewModel.errorMessage.value = "Invalid credentials. Please try again."
                        }
                    } else {
                        loginViewModel.errorMessage.value = "Username and password cannot be empty."
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor()),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = if (isLoading) "Logging in..." else "Login", color = Color.White)
        }

        Text(
            text = "Don't have an account? Sign up",
            fontSize = 18.sp,
            color = appTextColor(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .clickable { navController.navigate("register") }
        )
    }
}
