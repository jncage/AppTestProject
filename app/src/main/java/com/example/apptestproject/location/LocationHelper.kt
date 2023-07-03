package com.example.apptestproject.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationHelper @Inject constructor(
    private val locationProvider: LocationProvider,
    private val geocodeProvider: GeocodeProvider
) {

    private var cityName = MutableLiveData<String>()
    fun getCityName(): LiveData<String> {
        return cityName
    }


    fun fetchCityName() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val locationData = locationProvider.getCurrentLocation()
                cityName.value = geocodeProvider.getCityName(locationData)
            } catch (_: Exception) {
            }
        }
    }

}
