package com.example.apptestproject.di

import com.example.apptestproject.adapters.location.FusedLocationProvider
import com.example.apptestproject.adapters.location.GeocodeProvider
import com.example.apptestproject.adapters.location.GeocodeProviderImpl
import com.example.apptestproject.adapters.location.LocationProvider
import dagger.Binds
import dagger.Module

@Module
abstract class LocationModule {

    @Binds
    abstract fun provideLocationProvider(locationProvider: FusedLocationProvider): LocationProvider

    @Binds
    abstract fun provideGeocodeProvider(geocodeProvider: GeocodeProviderImpl): GeocodeProvider
}
