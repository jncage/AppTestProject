package com.example.apptestproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptestproject.api.DishesApiService
import com.example.apptestproject.models.Dish
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
            val tags = dishes.flatMap {
                it.tags
            }.distinct()
            _tagsLiveData.postValue(tags)
        }
    }


}