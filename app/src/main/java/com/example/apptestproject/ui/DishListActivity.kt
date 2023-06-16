package com.example.apptestproject.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R
import com.example.apptestproject.adapters.TagAdapter
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.network.ApiClient
import com.example.apptestproject.viewmodels.DishesViewModel

class DishListActivity : AppCompatActivity() {
    private val tag = this.javaClass.simpleName
    private lateinit var dishesApiService: DishesApiService
    private lateinit var dishesViewModel: DishesViewModel
    private lateinit var tagAdapter: TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)
        Log.d(tag, "DishListActivity created")
        dishesApiService = ApiClient.createDishesApiService(this)
        dishesViewModel = DishesViewModel(dishesApiService)
        dishesViewModel.tagsLiveData.observe(this) {
            Log.d(tag, "Tags are $it")
            tagAdapter.updateTags(it)
        }
        val tagRecyclerView = findViewById<RecyclerView>(R.id.tags)
        tagAdapter = TagAdapter()
        tagAdapter.setSelectedItemPosition(0)
        tagRecyclerView.adapter = tagAdapter
        dishesViewModel.fetchDishes()
        val categoryName = intent.getStringExtra("categoryName")
        val categoryNameTextView = findViewById<TextView>(R.id.categoryName)
        categoryNameTextView.text = categoryName
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }


    }
}