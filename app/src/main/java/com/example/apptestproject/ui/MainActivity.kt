package com.example.apptestproject.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.apptestproject.R
import com.example.apptestproject.model.Categories
import com.example.apptestproject.model.Category
import com.example.apptestproject.utils.CategoriesDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Activity created")

        // Make API request to get categories
        val apiService = ApiClient.apiService
        Log.d(TAG, "apiService created: $apiService")
        val call = apiService.getCategories()
        Log.d(TAG, "call created: $call")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    if (responseBody != null) {
                        val gson = Gson()
                        val jsonObject = gson.fromJson(responseBody, Map::class.java)

                        val categoriesArray = jsonObject.values
                            .flatMap { value ->
                                if (value is List<*>) value else emptyList()
                            }
                            .filterIsInstance<Map<*, *>>()
                            .firstOrNull()

                        if (categoriesArray != null) {
                            val categories = categoriesArray.values
                                .filterIsInstance<Map<*, *>>()
                                .map { categoryData ->
                                    gson.fromJson(gson.toJson(categoryData), Category::class.java)
                                }
                            categories.forEach { Log.d(TAG, "Category is $it") }

                            // Do something with the category objects
                        } else {
                            Log.d(TAG, "No categories array found")
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    Log.d(TAG, "No response ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
            }
        })
    }


}