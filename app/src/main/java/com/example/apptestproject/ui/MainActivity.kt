package com.example.apptestproject.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.models.Category
import com.example.apptestproject.network.ApiClient
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.utils.LocationHelper
import com.example.apptestproject.viewmodels.CategoryViewModel
import com.example.apptestproject.viewmodels.LocationViewModel
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val tag = this.javaClass.simpleName

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var locationHelper: LocationHelper
    private lateinit var cityNameTextView: TextView

    @Inject
    lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "Activity created")
        (application as MyApp).appComponent.inject(this)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val currentDate = findViewById<TextView>(R.id.currentDateTextView)
        cityNameTextView = findViewById<TextView>(R.id.cityTextView)
        currentDate.text = DateUtil.getCurrentDate()
        locationHelper.locationLiveData.observe(this) { cityName ->
            Log.d("LocationHelper", "Livedata in mainActivity is $cityName")
            cityNameTextView.text = cityName
            sharedPreferences.edit().putString("cityName", cityName).apply()
        }
        categoryViewModel.categoriesLiveData.observe(this) { categories ->
            updateCategories(categories)
        }
        categoryViewModel.fetchCategories()

        locationHelper.checkLocationPermission()
        val cartSection = findViewById<LinearLayout>(R.id.cartSection)
        cartSection.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cityNameTextView.text = sharedPreferences.getString("cityName", "")
    }

    private fun updateCategories(categories: List<Category>) {
        categories.forEachIndexed { index, category ->
            Log.d(tag, "Category is $category")
            val categoryButtons = resources.obtainTypedArray(R.array.category_buttons)
            val buttonId = categoryButtons.getResourceId(index, 0)
            categoryButtons.recycle()
            val categoryButton = findViewById<RelativeLayout>(buttonId)
            categoryButton.setOnClickListener {
                val intent = Intent(this, DishListActivity::class.java).apply {
                    putExtra("categoryName", category.name)
                }
                startActivity(intent)
            }
            val backgroundImage =
                categoryButton.findViewById<ImageView>(R.id.backgroundImage)
            backgroundImage.clipToOutline = true
            val buttonName = categoryButton.findViewById<TextView>(R.id.buttonName)
            buttonName.text = category.name
            picasso.load(category.imageUrl).into(backgroundImage)
        }
    }
}
