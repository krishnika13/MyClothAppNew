package com.example.myapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var selectedGender = mutableStateOf("Male")
    var isLoading = mutableStateOf(false)
}
