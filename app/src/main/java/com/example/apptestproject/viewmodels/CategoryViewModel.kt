package com.example.apptestproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptestproject.api.CategoriesApiService
import com.example.apptestproject.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoriesApiService: CategoriesApiService) :
    ViewModel() {
    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>>
        get() = _categoriesLiveData

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categories = categoriesApiService.getCategories()
                _categoriesLiveData.postValue(categories)
            } catch (e: Exception) {
                Log.d("MainActivity", "Cannot receive a list of categories")
            }
        }
    }
}