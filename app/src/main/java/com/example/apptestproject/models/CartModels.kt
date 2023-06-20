package com.example.apptestproject.models

data class CartItem(val dish: Dish, var quantity: Int)

data class CartData(val cartItems: List<CartItem>, val totalPrice: Int)
