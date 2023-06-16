package com.example.apptestproject.api

import com.example.apptestproject.models.Dishes
import retrofit2.http.GET

interface DishesApiService {
    @GET("v3/c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): Dishes
}