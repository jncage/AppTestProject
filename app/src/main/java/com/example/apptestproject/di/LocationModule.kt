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
class LocationModule {
//    @Provides
//    fun provideApplication(application: Application): Application {
//        return application
//    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideLocationHelper(
        fragmentActivity: FragmentActivity,
        locationProvider: FusedLocationProvider,
        geocodeProvider: GeocodeHelper
    ): LocationHelper {
        return LocationHelper(fragmentActivity, locationProvider, geocodeProvider)
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
