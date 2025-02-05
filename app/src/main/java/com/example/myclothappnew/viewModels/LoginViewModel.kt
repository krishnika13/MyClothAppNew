package com.example.myclothappnew.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // State variables to hold the login data
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    // Function to reset error messages after a successful login attempt
    fun resetErrorMessage() {
        errorMessage.value = null
    }

    // Function to validate login data
    fun isLoginDataValid(): Boolean {
        return username.value.isNotBlank() && password.value.isNotBlank()
    }
}
