package com.example.healthysteps.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodFactsApi {

    @GET("api/v2/product/{barcode}")
    suspend fun getProduct(
        @Path("barcode") barcode: String
    ): OpenFoodFactsResponse
}