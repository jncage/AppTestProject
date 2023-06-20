package com.example.apptestproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R
import com.example.apptestproject.adapters.DishAdapter
import com.example.apptestproject.adapters.TagAdapter
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.models.Dish
import com.example.apptestproject.network.ApiClient
import com.example.apptestproject.viewmodels.DishesViewModel
import com.squareup.picasso.Picasso

class DishListActivity : AppCompatActivity() {
    private val tag = this.javaClass.simpleName
    private lateinit var dishesApiService: DishesApiService
    private lateinit var dishesViewModel: DishesViewModel
    private lateinit var tagAdapter: TagAdapter
    private lateinit var dishAdapter: DishAdapter
    private lateinit var dishRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)
        Log.d(tag, "DishListActivity created")
        dishesApiService = ApiClient.createDishesApiService(this)
        dishesViewModel = DishesViewModel(dishesApiService)

        val tagRecyclerView = findViewById<RecyclerView>(R.id.tagsList)
        dishRecyclerView = findViewById(R.id.dishesList)
        tagAdapter = TagAdapter { tag ->
            val filteredDishes = dishesViewModel.filterDishesByTag(tag)
            setDishesAdapter(filteredDishes)
        }
        tagAdapter.setSelectedItemPosition(0)
        tagRecyclerView.adapter = tagAdapter
        dishAdapter = DishAdapter {dish ->
            showAddToCartPopup(dish)
        }
        dishRecyclerView.adapter = dishAdapter


        dishesViewModel.tagsLiveData.observe(this) {
            Log.d(tag, "Tags are $it")
            tagAdapter.updateTags(it)
        }
        dishesViewModel.dishesLiveData.observe(this) {
            Log.d(tag, "Dishes are $it")
            setDishesAdapter(it)
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
        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_add_to_cart, null)
        val builder = AlertDialog.Builder(this)

        val dishName = popupView.findViewById<TextView>(R.id.dishNameView)
        val dishImage = popupView.findViewById<ImageView>(R.id.dishImagePopUp)
        val dishPrice = popupView.findViewById<TextView>(R.id.priceView)
        dishPrice.text = "${dish.price} ₽"
        val dishWeight = popupView.findViewById<TextView>(R.id.weightView)
        dishWeight.text = " · ${dish.weight}г"
        val dishDescription = popupView.findViewById<TextView>(R.id.descriptionView)
        dishDescription.text = dish.description
        Picasso.get().load(dish.imageUrl).into(dishImage)
        dishName.text = dish.name
        builder.setView(popupView)
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.cornered_shape_15dp)
        alertDialog.show()

        // Set up the "Add to Cart" button
        val addToCartButton = popupView.findViewById<TextView>(R.id.addToCartButton)
        addToCartButton.setOnClickListener {
            // Perform the action of adding the dish to the cart
            dishesViewModel.addToCart(dish)
            alertDialog.dismiss() // Close the pop-up window
        }

        // Set up the dismiss button
        val dismissButton = popupView.findViewById<ImageView>(R.id.closeButton)
        dismissButton.setOnClickListener {
            alertDialog.dismiss() // Close the pop-up window
        }


    }

    private fun setDishesAdapter(dishes: List<Dish>) {
        dishAdapter.updateDishes(dishes)
    }
}