package com.example.apptestproject.repositories

import com.example.apptestproject.models.CartData
import com.example.apptestproject.models.CartItem

object CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(cartItem: CartItem) {
        val existingItem = cartItems.find { it.dish.id == cartItem.dish.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(cartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        val existingItem = cartItems.find { it.dish.id == cartItem.dish.id }
        if (existingItem!!.quantity > 1) {
            existingItem.quantity--
        } else {
            cartItems.remove(cartItem)
        }
    }

    fun getCartData(): CartData {
        val totalPrice = calculateTotalPrice()
        return CartData(cartItems, totalPrice)
    }

    private fun calculateTotalPrice(): Int {
        return cartItems.sumOf { it.dish.price * it.quantity }
    }

}
