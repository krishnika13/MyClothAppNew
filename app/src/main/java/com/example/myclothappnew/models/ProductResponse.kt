package com.example.myclothappnew.models

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val size: List<String>,
    val color: List<String>

)
