package com.example.apptestproject.repositories

import com.example.apptestproject.models.CartData
import com.example.apptestproject.models.CartItem
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor() : CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override fun addToCart(cartItem: CartItem) {
        val existingItem = cartItems.find { it.dish.id == cartItem.dish.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(cartItem)
        }
    }

    override fun removeFromCart(cartItem: CartItem) {
        val existingItem = cartItems.find { it.dish.id == cartItem.dish.id }
        if (existingItem!!.quantity > 1) {
            existingItem.quantity--
        } else {
            cartItems.remove(cartItem)
        }
    }

    override fun getCartData(): CartData {
        val totalPrice = calculateTotalPrice()
        return CartData(cartItems, totalPrice)
    }

    private fun calculateTotalPrice(): Int {
        return cartItems.sumOf { it.dish.price * it.quantity }
    }

}
