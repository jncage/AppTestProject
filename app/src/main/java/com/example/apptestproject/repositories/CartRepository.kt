package com.example.apptestproject.repositories

import com.example.apptestproject.models.CartData
import com.example.apptestproject.models.CartItem

interface CartRepository {
    fun addToCart(cartItem: CartItem)
    fun removeFromCart(cartItem: CartItem)
    fun getCartData(): CartData
}
