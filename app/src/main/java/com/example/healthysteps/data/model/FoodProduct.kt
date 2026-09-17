package com.example.healthysteps.data.model

data class FoodProduct(
    val barcode: String = "",
    val name: String = "Unknown product",
    val brand: String = "",
    val calories: String = "N/A",
    val protein: String = "N/A",
    val carbohydrates: String = "N/A",
    val fat: String = "N/A",
    val imageUrl: String = ""
)