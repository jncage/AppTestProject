package com.example.apptestproject.models

import com.google.gson.annotations.SerializedName
data class Dishes(val dishes: List<Dish>)
data class Dish(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    var description: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("tegs")
    val tags: List<String>
)
