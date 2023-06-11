package com.example.apptestproject.ui

import com.example.apptestproject.network.ApiClient.apiService
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.apptestproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class MainActivity : ComponentActivity() {
//    private val TAG = "MainActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        Log.d(TAG, "Activity created")
//
//        // Create a coroutine scope
//        val mainScope = CoroutineScope(Dispatchers.Main)
//
//        // Launch a coroutine
//        mainScope.launch {
//            try {
//                val response = apiService.getCategories()
//                if (response.isSuccessful) {
//                    val responseData = response.body()
//                    responseData?.categories?.forEach { category ->
//                        Log.d(TAG, "Category is $category")
//                    }
//
//                    // Do something with the category objects
//                } else {
//                    // Handle unsuccessful response
//                    Log.e(TAG, "API call failed: ${response.code()}")
//                }
//            } catch (e: Exception) {
//                // Handle network error
//                Log.e(TAG, "Network error: ${e.message}")
//            }
//        }
//    }
//}
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Activity created")

        // Create a coroutine scope
        val mainScope = CoroutineScope(Dispatchers.Main)

        // Launch a coroutine
        mainScope.launch {
            try {
                val categories = apiService.getCategories()
                categories.forEach { category ->
                    Log.d(TAG, "Category is $category")
                }

                // Do something with the category objects
            } catch (e: Exception) {
                // Handle network error
                Log.e(TAG, "Network error: ${e.message}")
            }
        }
    }
}