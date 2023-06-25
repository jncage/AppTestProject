package com.example.apptestproject.ui

import android.content.Intent
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
    private val tag = this.javaClass.simpleName
    private lateinit var apiService: CategoriesApiService
    private lateinit var picasso: Picasso
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var locationViewModel: LocationViewModel
    @Inject
    lateinit var locationHelper: LocationHelper
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val myApp = application as MyApp
        myApp.initializeLocationComponent(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "Activity created")

        myApp.locationComponent.inject(this)
        apiService = ApiClient.createCategoriesApiService(this)
        categoryViewModel = CategoryViewModel(apiService)
        okHttpClient = ApiClient.createOkHttpClient(this)
        picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()
        val currentDate = findViewById<TextView>(R.id.currentDateTextView)
        val cityNameTextView = findViewById<TextView>(R.id.cityTextView)
        currentDate.text = DateUtil.getCurrentDate()
        locationViewModel = LocationViewModel(locationHelper)
        locationViewModel.cityNameLiveData.observe(this) { cityName ->
            cityNameTextView.text = cityName
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

    private fun updateCategories(categories: List<Category>) {
        categories.forEachIndexed { index, category ->
            Log.d(tag, "Category is $category")
            val categoryButtons = resources.obtainTypedArray(R.array.category_buttons)
            val buttonId = categoryButtons.getResourceId(index, 0)
            categoryButtons.recycle()
//                resources.getIdentifier("category${index + 1}", "id", packageName)
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
