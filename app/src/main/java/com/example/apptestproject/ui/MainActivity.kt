package com.example.apptestproject.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.apptestproject.R
import com.example.apptestproject.api.MyApiService
import com.example.apptestproject.network.ApiClient
import com.squareup.picasso.Cache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    private val _aTAG = this.javaClass.simpleName
    private lateinit var apiService: MyApiService
    private lateinit var picasso: Picasso
    private lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(_aTAG, "Activity created")

        // Initialize the API service and OkHttpClient
        apiService = ApiClient.createApiService(this)
        okHttpClient = ApiClient.createOkHttpClient(this)
        picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(okHttpClient))
            .memoryCache(Cache.NONE) // Disable memory caching
            .build()

        // Create a coroutine scope
        val mainScope = CoroutineScope(Dispatchers.Main)

        // Launch a coroutine
        mainScope.launch {
            try {
                val categories = apiService.getCategories()
                categories.forEachIndexed { index, category ->
                    Log.d(_aTAG, "Category is $category")

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

                // Do something with the category objects
            } catch (e: Exception) {
                // Handle network error
                Log.e(_aTAG, "Network error: ${e.message}")
            }
        }
    }
}



