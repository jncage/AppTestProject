package com.example.apptestproject.network

import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.api.DishesApiService

interface ApiClient {
    fun createDishesApiService(): DishesApiService
    fun createCategoriesApiService(): CategoriesApiService
}
