package com.example.apptestproject.utils

import com.example.apptestproject.models.LocationData

interface GeocodeProvider {
    fun getCityName(locationData: LocationData): String
}