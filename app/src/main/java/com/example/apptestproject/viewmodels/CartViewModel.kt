package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptestproject.models.CartData
import com.example.apptestproject.models.CartItem
import com.example.apptestproject.repositories.CartRepository
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _cartData = MutableLiveData<CartData>()
    val cartData: LiveData<CartData> get() = _cartData

    fun addToCart(cartItem: CartItem) {
        repository.addToCart(cartItem)
        updateCartData()
    }

    fun removeFromCart(cartItem: CartItem) {
        repository.removeFromCart(cartItem)
        updateCartData()
    }

    fun updateCartData() {
        val cartData = repository.getCartData()
        _cartData.value = cartData
    }
}
