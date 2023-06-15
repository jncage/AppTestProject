package com.example.apptestproject.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.apptestproject.R
import com.example.apptestproject.api.MyApiService
import com.example.apptestproject.network.ApiClient
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.utils.FusedLocationProvider
import com.example.apptestproject.utils.GeocodeHelper
import com.example.apptestproject.utils.GeocodeProvider
import com.example.apptestproject.utils.LocationHelper
import com.example.apptestproject.utils.LocationProvider
import com.example.apptestproject.viewmodels.LocationViewModel
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    private val tag = this.javaClass.simpleName
    private lateinit var apiService: MyApiService
    private lateinit var picasso: Picasso
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var cityNameTextView: TextView
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "Activity created")

        // Initialize the API service and OkHttpClient
        apiService = ApiClient.createApiService(this)
        okHttpClient = ApiClient.createOkHttpClient(this)
        picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()
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


        // Create a coroutine scope
        val mainScope = CoroutineScope(Dispatchers.Main)

        // Launch a coroutine
        mainScope.launch {
            try {
                val categories = apiService.getCategories()
                categories.forEachIndexed { index, category ->
                    Log.d(tag, "Category is $category")

                    // Find the corresponding category button by ID
                    val buttonId =
                        resources.getIdentifier("category${index + 1}", "id", packageName)
                    val categoryButton = findViewById<RelativeLayout>(buttonId)

                    // Find the views within the category button
                    val backgroundImage =
                        categoryButton.findViewById<ImageView>(R.id.backgroundImage)
                    val buttonName = categoryButton.findViewById<TextView>(R.id.buttonName)

                    // Set the category name on the button
                    buttonName.text = category.name

                    // Load the image using Picasso into the ImageView
                    picasso
                        .load(category.imageUrl)
                        .into(backgroundImage)
                }
            } catch (e: Exception) {
                // Handle network error
                Log.e(tag, "Network error: ${e.message}")
            }
        }

    }

    private fun updateCityName(cityName: String) {
        cityNameTextView.text = cityName
    }
}
