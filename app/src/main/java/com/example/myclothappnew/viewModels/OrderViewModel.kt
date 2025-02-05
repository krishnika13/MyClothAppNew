package com.example.myclothappnew.viewModels


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class OrderViewModel : ViewModel() {
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var paymentMethod = mutableStateOf("Cash")
    var cardNumber = mutableStateOf("")
    var expiryDate = mutableStateOf("")
    var cvv = mutableStateOf("")
    var orderPlaced = mutableStateOf(false)

    fun placeOrder() {
        orderPlaced.value = true
    }
}
