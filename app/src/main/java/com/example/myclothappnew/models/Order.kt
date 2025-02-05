package com.example.myclothappnew.models

data class Order(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val paymentMethod: String,
    val cardNumber: String? = null,
    val expiryDate: String? = null,
    val cvv: String? = null
)