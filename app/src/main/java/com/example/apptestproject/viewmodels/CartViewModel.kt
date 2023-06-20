package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptestproject.models.CartData
import com.example.apptestproject.models.CartItem
import com.example.apptestproject.repositories.CartRepository

class CartViewModel : ViewModel() {
    private val _cartData = MutableLiveData<CartData>()
    val cartData: LiveData<CartData> get() = _cartData

    fun addToCart(cartItem: CartItem) {
        CartRepository.addToCart(cartItem)
        updateCartData()
    }

    fun removeFromCart(cartItem: CartItem) {
        CartRepository.removeFromCart(cartItem)
        updateCartData()
    }

    fun updateCartData() {
        val cartData = CartRepository.getCartData()
        _cartData.value = cartData
    }
}
