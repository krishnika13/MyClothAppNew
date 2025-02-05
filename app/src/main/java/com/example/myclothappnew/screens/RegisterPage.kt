package com.example.myclothappnew.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.myclothappnew.data.FirebaseAuthService
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import appBackgroundColor
import appTextColor
import buttonColor
import com.example.myapp.viewmodels.RegisterViewModel // Import the RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavHostController) {
    // Use the ViewModel to retain state across configuration changes
    val registerViewModel: RegisterViewModel = viewModel()

    val username by remember { registerViewModel.username }
    val email by remember { registerViewModel.email }
    val password by remember { registerViewModel.password }
    val isLoading by remember { registerViewModel.isLoading }
    val selectedGender by remember { registerViewModel.selectedGender }

    val firebaseAuthService = FirebaseAuthService()
    val coroutineScope = rememberCoroutineScope()

    fun handleRegister() {
        if (username.isBlank() || email.isBlank() || password.isBlank()) return
        registerViewModel.isLoading.value = true
        coroutineScope.launch {
            val result = firebaseAuthService.registerUser(email, password)
            registerViewModel.isLoading.value = false
            if (result != null) {
                navController.navigate("available_products")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackgroundColor()),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "REGISTER NOW", color = appTextColor(), fontSize = 40.sp)
                TextField(
                    value = username,
                    onValueChange = { registerViewModel.username.value = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = email,
                    onValueChange = { registerViewModel.email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = password,
                    onValueChange = { registerViewModel.password.value = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Column {
                    Text("Gender:", color = appTextColor())
                    listOf("Male", "Female", "Other").forEach { gender ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedGender == gender,
                                onClick = { registerViewModel.selectedGender.value = gender }
                            )
                            Text(gender, color = appTextColor())
                        }
                    }
                }
                Button(
                    onClick = { handleRegister() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor()),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    Text(text = "Register", fontSize = 21.sp)
                }
            }
        }
    }
}
