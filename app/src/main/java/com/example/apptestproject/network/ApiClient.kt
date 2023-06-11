package com.example.apptestproject.network

import android.content.Context
import com.example.apptestproject.api.MyApiService
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

    private val gson = GsonBuilder()
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

    private fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient(context))
            .build()
    }

    fun createApiService(context: Context): MyApiService {
        return createRetrofit(context).create(MyApiService::class.java)
    }
}




