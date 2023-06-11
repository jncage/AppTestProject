package com.example.apptestproject.network

import com.example.apptestproject.api.MyApiService
import com.example.apptestproject.utils.CategoriesDeserializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object com.example.apptestproject.network.ApiClient {
//    private const val BASE_URL = "https://run.mocky.io/"
//
//    private val gson = GsonBuilder()
//        .registerTypeAdapter(object : TypeToken<List<Category>>() {}.type, CategoriesDeserializer())
//        .create()
//
//
//    private val retrofit: Retrofit by lazy {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(client)
//            .build()
//    }
//
//    val apiService: MyApiService by lazy {
//        retrofit.create(MyApiService::class.java)
//    }

object ApiClient {
    private const val BASE_URL = "https://run.mocky.io/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(List::class.java, CategoriesDeserializer())
        .create()

    private val retrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val apiService: MyApiService by lazy {
        retrofit.create(MyApiService::class.java)
    }
}


