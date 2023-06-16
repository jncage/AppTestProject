package com.example.apptestproject.api

import com.example.apptestproject.models.Category
import retrofit2.http.GET

interface CategoriesApiService {
    @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): List<Category>
}
