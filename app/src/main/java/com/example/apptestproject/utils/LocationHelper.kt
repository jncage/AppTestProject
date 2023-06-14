package com.example.apptestproject.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.apptestproject.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class LocationHelper(private val activity: FragmentActivity) {
    private val tag = this.javaClass.simpleName
    private val locationClient = LocationServices.getFusedLocationProviderClient(activity)
    private lateinit var locationCallback: LocationCallback

    private val requestLocationPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startLocationUpdates()
            } else {
                Log.d(tag, "Location permission denied")
            }
        }

    fun checkLocationPermission() {
        if (isLocationPermissionGranted()) {
            startLocationUpdates()
        } else {
            requestLocationPermission()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        return ContextCompat.checkSelfPermission(activity, locationPermission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        requestLocationPermissionLauncher.launch(locationPermission)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d(tag, "In onLocationResult")
                    CoroutineScope(Dispatchers.Main).launch {
                        val cityName = getCityName(location)
                        val cityTextView = activity.findViewById<TextView>(R.id.cityTextView)
                        cityTextView.text = cityName
                        Log.d(tag, "City name is $cityName")
                    }
                }
            }
        }

        val locationRequest = LocationRequest.Builder(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    private suspend fun getCityName(location: Location): String? = withContext(Dispatchers.IO) {
        Log.d(tag, "in getCityName function")
        val geocoder = Geocoder(activity, Locale.getDefault())
        var cityName: String? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            cityName = addresses[0].locality
                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)
                            Log.d(tag, "in onError $errorMessage")
                        }
                    })
            } catch (e: IOException) {
                Log.e(tag, "Error geocoding location: ${e.message}")
            }
        } else {
            try {
                @Suppress("DEPRECATION") val addresses =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    cityName = addresses[0].locality
                } else {
                    Log.d(tag, "No matching addresses found")
                }
            } catch (e: IOException) {
                Log.e(tag, "Error geocoding location: ${e.message}")
            }
        }

        cityName
    }

    fun removeLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }
}
