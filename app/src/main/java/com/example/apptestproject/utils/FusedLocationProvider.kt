package com.example.apptestproject.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.apptestproject.models.LocationData
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FusedLocationProvider(context: Context) : LocationProvider {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.Builder(10000)
        .setMinUpdateIntervalMillis(5000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationData {
        return suspendCancellableCoroutine { continuation ->
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        continuation.resume(LocationData(location.latitude, location.longitude))
                    } ?: run {
                        continuation.resumeWithException(Exception("Failed to retrieve location"))
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )

            continuation.invokeOnCancellation {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}