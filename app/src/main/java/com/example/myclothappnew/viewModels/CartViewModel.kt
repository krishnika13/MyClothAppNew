package com.example.myclothappnew.viewModels


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myclothappnew.models.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems = _cartItems

    fun addItemToCart(item: CartItem) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeItemFromCart(item: CartItem) {
        _cartItems.value = _cartItems.value.filter { it != item }
    }
}
