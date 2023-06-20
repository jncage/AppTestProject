package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.models.CartItem
import com.example.apptestproject.models.Dish
import com.example.apptestproject.repositories.CartRepository
import kotlinx.coroutines.launch

class DishesViewModel(private val dishesApiService: DishesApiService) : ViewModel() {
    private val _tagsLiveData = MutableLiveData<List<String>>()
    private lateinit var dishes: List<Dish>
    val tagsLiveData: LiveData<List<String>>
        get() = _tagsLiveData
    private val _dishesLiveData = MutableLiveData<List<Dish>>()
    val dishesLiveData: LiveData<List<Dish>>
        get() = _dishesLiveData

    fun fetchDishes() {
        viewModelScope.launch {
            dishes = dishesApiService.getDishes().dishes
            val tortellini = dishes.find { it.id == 4 }
            val imageUrl = tortellini?.description
            val desc = "Тортеллини - это итальянская паста, которая обычно имеет форму круглых пельменей или больших домашних пельменей. Они сделаны из тонкого слоя теста, которое обычно начиняется смесью мяса, рыбы, сыра или овощей. Тортеллини обычно варятся в кипящей воде до готовности и подаются с различными соусами, такими как соус на основе томатов, сливочный соус или песто. Они являются популярным и вкусным блюдом и широко распространены в итальянской кухне."
            if (tortellini != null) {
                tortellini.description = desc
            }
            if (imageUrl != null) {
                tortellini.imageUrl = imageUrl
            }
            _dishesLiveData.postValue(dishes)
            val tags = dishes.flatMap {
                it.tags
            }.distinct()
            _tagsLiveData.postValue(tags)
        }
    }
    fun filterDishesByTag(tag: String): List<Dish> {
        return dishes.filter { dish -> dish.tags.any { it == tag } }
    }

    fun addToCart(dish: Dish) {
        CartRepository.addToCart(CartItem(dish, 1))
    }

}