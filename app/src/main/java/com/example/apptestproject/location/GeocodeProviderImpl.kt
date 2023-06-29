package com.example.apptestproject.location

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.example.apptestproject.models.LocationData
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class GeocodeProviderImpl @Inject constructor(private val context: Context) : GeocodeProvider {
    private val tag = this.javaClass.simpleName
    private val geocoder = Geocoder(context, Locale.getDefault())
    private var cityName: String? = null
    override fun getCityName(locationData: LocationData): String {
        try {
            @Suppress("DEPRECATION") val addresses =
                geocoder.getFromLocation(locationData.latitude, locationData.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                cityName = addresses[0].locality
            } else {
                Log.d(tag, "No matching addresses found")
            }
        } catch (e: IOException) {
            Log.e(tag, "Error geocoding location: ${e.message}")
        }
        return cityName ?: "Unknown city"
    }
}