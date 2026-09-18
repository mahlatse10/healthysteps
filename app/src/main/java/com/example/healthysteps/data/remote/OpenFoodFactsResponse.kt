package com.example.healthysteps.data.remote

data class OpenFoodFactsResponse(
    val status: Int? = null,
    val product: ProductData? = null
)

data class ProductData(
    val product_name: String? = null,
    val brands: String? = null,
    val image_url: String? = null,
    val nutriments: Nutriments? = null
)

data class Nutriments(
    val energy_kcal_100g: Double? = null,
    val proteins_100g: Double? = null,
    val carbohydrates_100g: Double? = null,
    val fat_100g: Double? = null
)