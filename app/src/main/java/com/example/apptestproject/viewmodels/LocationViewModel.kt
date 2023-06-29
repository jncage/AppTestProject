package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apptestproject.location.LocationHelper

class LocationViewModel(
    locationHelper: LocationHelper
) : ViewModel() {

    val cityNameLiveData: LiveData<String> = locationHelper.locationLiveData
}
