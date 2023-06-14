package com.example.apptestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apptestproject.R
import com.example.apptestproject.utils.LocationHelper

class MainActivity : AppCompatActivity() {
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationHelper = LocationHelper(this)

        locationHelper.checkLocationPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationHelper.removeLocationUpdates()
    }
}
