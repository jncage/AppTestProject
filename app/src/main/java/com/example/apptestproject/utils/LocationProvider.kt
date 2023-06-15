package com.example.apptestproject.utils

import com.example.apptestproject.models.LocationData

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationData
}
