package com.example.apptestproject.network

import android.content.Context
import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.utils.CategoriesDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Inject

class ApiClientImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val context: Context
) : ApiClient {
    private val BASE_URL = "https://run.mocky.io/"

    private val categoriesGson = GsonBuilder()
        .registerTypeAdapter(List::class.java, CategoriesDeserializer())
        .create()

    private fun createRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient())
            .build()
    }

    override fun createDishesApiService(): DishesApiService {
        return createRetrofit(Gson()).create(DishesApiService::class.java)
    }

    override fun createCategoriesApiService(): CategoriesApiService {
        return createRetrofit(categoriesGson).create(CategoriesApiService::class.java)
    }
}
