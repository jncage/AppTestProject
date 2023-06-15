package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apptestproject.utils.LocationHelper

class LocationViewModel(
    private val locationHelper: LocationHelper
) : ViewModel() {

    val cityNameLiveData: LiveData<String> = locationHelper.locationLiveData

}
