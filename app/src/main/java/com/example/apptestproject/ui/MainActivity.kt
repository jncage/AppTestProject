package com.example.apptestproject.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.apptestproject.R
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.utils.FusedLocationProvider
import com.example.apptestproject.utils.GeocodeHelper
import com.example.apptestproject.utils.GeocodeProvider
import com.example.apptestproject.utils.LocationHelper
import com.example.apptestproject.utils.LocationProvider
import com.example.apptestproject.viewmodels.LocationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var cityNameTextView: TextView
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentDate = findViewById<TextView>(R.id.currentDateTextView)
        cityNameTextView = findViewById(R.id.cityTextView)
        currentDate.text = DateUtil.getCurrentDate()
        val locationProvider: LocationProvider = FusedLocationProvider(this)
        val geocodeProvider: GeocodeProvider = GeocodeHelper(this)
        locationHelper = LocationHelper(this, locationProvider, geocodeProvider)
        locationViewModel = LocationViewModel(locationHelper)
        locationViewModel.cityNameLiveData.observe(this) { cityName ->
            updateCityName(cityName)
        }
        locationHelper.checkLocationPermission()
    }

    private fun updateCityName(cityName: String) {
        cityNameTextView.text = cityName
    }
}
