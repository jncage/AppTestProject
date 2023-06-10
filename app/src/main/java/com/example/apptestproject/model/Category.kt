package com.example.apptestproject.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    val name: String,
    @SerializedName("image_url") val imageUrl: String
)

