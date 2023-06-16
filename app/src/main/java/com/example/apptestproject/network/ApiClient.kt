package com.example.apptestproject.network

import android.content.Context
import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.utils.CategoriesDeserializer
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object ApiClient {
    private const val BASE_URL = "https://run.mocky.io/"

    private val categoriesGson = GsonBuilder()
        .registerTypeAdapter(List::class.java, CategoriesDeserializer())
        .create()


    fun createOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = (10 * 1024 * 1024).toLong() // 10MB

        // Create the cache
        val cache = Cache(cacheDirectory, cacheSize)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()
    }

    private fun createCategoriesRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(categoriesGson))
            .client(createOkHttpClient(context))
            .build()
    }
    private fun createDishesRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient(context))
            .build()
    }
    fun createDishesApiService(context: Context): DishesApiService {
        return createDishesRetrofit(context).create(DishesApiService::class.java)
    }
    fun createCategoriesApiService(context: Context): CategoriesApiService {
        return createCategoriesRetrofit(context).create(CategoriesApiService::class.java)
    }
}




