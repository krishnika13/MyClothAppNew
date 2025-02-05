package com.example.myclothappnew.data

import com.example.myclothappnew.models.ProductResponse
import retrofit2.http.GET

interface ProductApiService {
    @GET("http://127.0.0.1:8000/product/5")  // Adjust this to your actual endpoint without the full URL, it's already handled in Retrofit
    suspend fun getProducts(): List<ProductResponse>
}
