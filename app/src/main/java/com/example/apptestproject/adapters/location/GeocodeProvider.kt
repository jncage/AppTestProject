package com.example.apptestproject.adapters.location

import com.example.apptestproject.models.LocationData

interface GeocodeProvider {
    fun getCityName(locationData: LocationData): String
}