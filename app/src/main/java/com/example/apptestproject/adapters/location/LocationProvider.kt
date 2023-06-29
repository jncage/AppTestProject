package com.example.apptestproject.adapters.location

import com.example.apptestproject.models.LocationData

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationData
}
