package com.example.apptestproject.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.apptestproject.R
import com.example.apptestproject.utils.LocationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val _aTAG = this.javaClass.simpleName
    private lateinit var locationUtil: LocationUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationUtil = LocationUtil(this)

        if (hasLocationPermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                val cityName = locationUtil.getCityName()
                Log.d(_aTAG, "City name: $cityName")
                updateCityName(cityName)
                loadCategories()
                displayCurrentDate()
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun updateCityName(cityName: String) {
        val cityNameTextView = findViewById<TextView>(R.id.cityTextView)
        cityNameTextView.text = cityName
    }

    private fun loadCategories() {
        // TODO: Load categories logic
    }

    private fun displayCurrentDate() {
        val currentDateTextView = findViewById<TextView>(R.id.currentDateTextView)
        val currentDate = getCurrentDate()
        currentDateTextView.text = currentDate
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CoroutineScope(Dispatchers.Main).launch {
                    val cityName = locationUtil.getCityName()
                    Log.d(_aTAG, "City name: $cityName")
                    updateCityName(cityName)
                }
            } else {
                val cityName = "Unknown"
                Log.d(_aTAG, "City name: $cityName")
                updateCityName(cityName)
            }
            loadCategories()
            displayCurrentDate()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
