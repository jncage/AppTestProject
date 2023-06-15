package com.example.apptestproject.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationHelper(
    private val activity: FragmentActivity,
    private val locationProvider: LocationProvider,
    private val geocodeProvider: GeocodeProvider
) {
    private val tag = this.javaClass.simpleName
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val _locationLiveData = MutableLiveData<String>()
    val locationLiveData: LiveData<String>
        get() = _locationLiveData

    fun checkLocationPermission() {
        if (isLocationPermissionGranted()) {
            startLocationUpdates()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        val requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startLocationUpdates()
                } else {
                    Log.d(tag, "Location permission denied")
                    // Handle permission denial if needed
                }
            }

        requestPermissionLauncher.launch(locationPermission)
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            locationPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationUpdates() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val locationData = locationProvider.getCurrentLocation()
                val cityName = geocodeProvider.getCityName(locationData)
                _locationLiveData.postValue(cityName)
            } catch (exception: Exception) {
                // Handle exception
                Log.d(tag, "Failed to receive current location")
            }
        }
    }
}
