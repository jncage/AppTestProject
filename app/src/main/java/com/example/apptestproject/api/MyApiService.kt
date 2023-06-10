package com.example.apptestproject.api

import com.example.apptestproject.model.Category
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface MyApiService {
    @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    fun getCategories(): Call<List<Category>>
}
