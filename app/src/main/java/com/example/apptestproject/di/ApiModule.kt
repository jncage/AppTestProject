package com.example.apptestproject.di

import android.content.Context
import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.network.ApiClient
import com.example.apptestproject.network.ApiClientImpl
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApiClient(impl: ApiClientImpl): ApiClient = impl

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = (10 * 1024 * 1024).toLong()
        return Cache(cacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideDishesApiService(apiClient: ApiClient): DishesApiService =
        apiClient.createDishesApiService()

    @Provides
    @Singleton
    fun provideCategoriesApiService(apiClient: ApiClient): CategoriesApiService =
        apiClient.createCategoriesApiService()
}