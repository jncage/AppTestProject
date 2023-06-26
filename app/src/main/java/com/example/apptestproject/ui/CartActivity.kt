package com.example.apptestproject.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.MyApp
import com.example.apptestproject.R
import com.example.apptestproject.adapters.CartAdapter
import com.example.apptestproject.utils.DateUtil
import com.example.apptestproject.viewmodels.CartViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartActivity : AppCompatActivity() {
    @Inject
    lateinit var cartViewModel: CartViewModel

    @Inject
    lateinit var picasso: Picasso

    private lateinit var cityNameTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_cart)
        cityNameTextView = findViewById(R.id.cityNameCart)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val cityName = sharedPreferences.getString("cityName", "")
        cityNameTextView.text = cityName
        val currentDateTextView = findViewById<TextView>(R.id.currentDateCart)
        currentDateTextView.text = DateUtil.getCurrentDate()
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val totalPrice = findViewById<TextView>(R.id.pay)
        val cartAdapter =
            CartAdapter(picasso, emptyList(), onPlusClick = { cartViewModel.addToCart(it) },
                onMinusClick = { cartViewModel.removeFromCart(it) })
        cartRecyclerView.adapter = cartAdapter
        cartViewModel.cartData.observe(this) {
            cartAdapter.updateCartItems(it.cartItems)
            totalPrice.text = getString(R.string.pay, getString(R.string.price, it.totalPrice))
        }
        cartViewModel.updateCartData()
        val homeSection = findViewById<LinearLayout>(R.id.homeSection)
        homeSection.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cityNameTextView.text = sharedPreferences.getString("cityName", "")

    }


}