package com.example.apptestproject.di

import com.example.apptestproject.location.FusedLocationProvider
import com.example.apptestproject.location.GeocodeProvider
import com.example.apptestproject.location.GeocodeProviderImpl
import com.example.apptestproject.location.LocationProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(provider: FusedLocationProvider): LocationProvider

    @Binds
    @Singleton
    abstract fun bindGeocodeProvider(provider: GeocodeProviderImpl): GeocodeProvider

}