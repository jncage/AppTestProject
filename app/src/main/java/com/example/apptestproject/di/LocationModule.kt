package com.example.apptestproject.di

import com.example.apptestproject.utils.FusedLocationProvider
import com.example.apptestproject.utils.GeocodeProviderImpl
import com.example.apptestproject.utils.GeocodeProvider
import com.example.apptestproject.utils.LocationProvider
import dagger.Binds
import dagger.Module

@Module
abstract class LocationModule {

    @Binds
    abstract fun provideLocationProvider(locationProvider: FusedLocationProvider): LocationProvider

    @Binds
    abstract fun provideGeocodeProvider(geocodeProvider: GeocodeProviderImpl): GeocodeProvider
}
