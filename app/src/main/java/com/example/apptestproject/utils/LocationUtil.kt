package com.example.apptestproject.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class LocationUtil(private val context: Context) {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    suspend fun getCityName(): String {
        return withContext(Dispatchers.IO) {
            val location = getLocation()
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    return@withContext addresses[0].locality ?: "Unknown"
                }
            }
            return@withContext "Unknown"
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLocation(): Location? {
        return withContext(Dispatchers.IO) {
            if (hasLocationPermission()) {
                val provider = LocationManager.GPS_PROVIDER
                return@withContext locationManager.getLastKnownLocation(provider)
            }
            return@withContext null
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
