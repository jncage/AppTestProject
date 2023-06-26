package com.example.apptestproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.adapters.DishAdapter
import com.example.apptestproject.adapters.TagAdapter
import com.example.apptestproject.models.Dish
import com.example.apptestproject.viewmodels.DishesViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DishListActivity : AppCompatActivity() {
    @Inject
    lateinit var dishesViewModel: DishesViewModel

    @Inject
    lateinit var picasso: Picasso
    private val tag = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)
        Log.d(tag, "DishListActivity created")
        (application as MyApp).appComponent.inject(this)
        val tagRecyclerView = findViewById<RecyclerView>(R.id.tagsList)
        val dishRecyclerView = findViewById<RecyclerView>(R.id.dishesList)
        val dishAdapter = DishAdapter(picasso) { dish ->
            showAddToCartPopup(dish)
        }
        val tagAdapter = TagAdapter { tag ->
            val filteredDishes = dishesViewModel.filterDishesByTag(tag)
            dishAdapter.updateDishes(filteredDishes)
        }
        tagAdapter.setSelectedItemPosition(0)
        tagRecyclerView.adapter = tagAdapter
        dishRecyclerView.adapter = dishAdapter
        dishesViewModel.tagsLiveData.observe(this) {
            Log.d(tag, "Tags are $it")
            tagAdapter.updateTags(it)
        }
        dishesViewModel.dishesLiveData.observe(this) {
            Log.d(tag, "Dishes are $it")
            dishAdapter.updateDishes(it)
        }
        dishesViewModel.fetchDishes()
        val categoryName = intent.getStringExtra("categoryName")
        val categoryNameTextView = findViewById<TextView>(R.id.categoryName)
        categoryNameTextView.text = categoryName
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val cartSection = findViewById<LinearLayout>(R.id.cartSection)
        cartSection.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAddToCartPopup(dish: Dish) {
        val addToCartDialog = AddToCartDialog(picasso, this, dish) {
            dishesViewModel.addToCart(dish)
        }
        addToCartDialog.show()
    }
}