package com.example.apptestproject.location

import com.example.apptestproject.models.LocationData

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationData
}
