package com.example.apptestproject.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.utils.FusedLocationProvider
import com.example.apptestproject.utils.GeocodeHelper
import com.example.apptestproject.utils.GeocodeProvider
import com.example.apptestproject.utils.LocationHelper
import com.example.apptestproject.utils.LocationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule(private val activity: FragmentActivity) {
    @Provides
    fun provideFragmentActivity(): FragmentActivity{
        return activity
    }
    @Provides
    fun provideFragmentContext(): Context {
        return activity
    }
    @Provides
    fun provideLocationProvider(context: Context): LocationProvider {
        return FusedLocationProvider(context)
    }

    @Provides
    fun provideGeocodeProvider(context: Context): GeocodeProvider {
        return GeocodeHelper(context)
    }
}
