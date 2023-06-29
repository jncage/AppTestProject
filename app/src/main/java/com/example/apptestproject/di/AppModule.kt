package com.example.apptestproject.di

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.repositories.CartRepository
import com.example.apptestproject.repositories.CartRepositoryImpl
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideFragmentActivity(context: Context): FragmentActivity {
        return context as FragmentActivity
    }

    @Singleton
    @Provides
    fun providePicasso(context: Context, okHttpClient: OkHttpClient): Picasso {
        return Picasso.Builder(context)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl()
    }
}
