package com.example.apptestproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R
import com.example.apptestproject.adapters.CartAdapter
import com.example.apptestproject.viewmodels.CartViewModel

class CartActivity : AppCompatActivity() {
    private val cartViewModel = CartViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val totalPrice = findViewById<TextView>(R.id.pay)
//        val cartAdapter = CartAdapter(emptyList(), onPlusClick = { cartViewModel.addToCart(it) },
//            onMinusClick = { cartViewModel.removeFromCart(it) })
//        cartRecyclerView.adapter = cartAdapter
        cartViewModel.cartData.observe(this) {
//            cartAdapter.updateCartItems(it.cartItems)
            cartRecyclerView.adapter = CartAdapter(it.cartItems, onPlusClick = { cartViewModel.addToCart(it) },
                onMinusClick = { cartViewModel.removeFromCart(it) })
            totalPrice.text = getString(R.string.pay, it.totalPrice)
        }
        cartViewModel.updateCartData()
        val homeSection = findViewById<LinearLayout>(R.id.homeSection)
        homeSection.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}