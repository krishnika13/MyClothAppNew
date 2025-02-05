package com.example.myclothappnew.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myclothappnew.data.RetrofitInstance
import com.example.myclothappnew.models.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<ProductResponse>>(emptyList())
    val products: StateFlow<List<ProductResponse>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val fetchedProducts = RetrofitInstance.apiService.getProducts() // Calls your API
                // Log to check the fetched products
                Log.d("ProductViewModel", "Fetched products: $fetchedProducts")
                _products.value = fetchedProducts
            } catch (e: Exception) {
                // Handle errors such as network failure
                Log.e("ProductViewModel", "Error fetching products: ${e.message}")
            }
        }
    }
}
