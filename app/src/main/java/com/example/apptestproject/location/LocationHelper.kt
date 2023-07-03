package com.example.apptestproject.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
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
    private val geocodeProvider: GeocodeProvider,
    private val context: Context
) {

    private var cityName = MutableLiveData<String>()
    fun getCityName(): LiveData<String> {
        return cityName
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun fetchCityName() {
        if (hasLocationPermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val locationData = locationProvider.getCurrentLocation()
                    cityName.value = geocodeProvider.getCityName(locationData)
                } catch (_: Exception) {
                }
            }
        } else {
            cityName.value = "Unknown"
        }
    }
}
