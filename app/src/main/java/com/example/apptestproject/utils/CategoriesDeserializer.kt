package com.example.apptestproject.utils

import com.example.apptestproject.model.Categories
import com.example.apptestproject.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CategoriesDeserializer : JsonDeserializer<Categories> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Categories {
        val categoriesJson = json?.asJsonObject?.getAsJsonArray("сategories")
        val categories = mutableListOf<Category>()

        categoriesJson?.forEach { categoryJson ->
            val id = categoryJson.asJsonObject.get("id").asInt
            val name = categoryJson.asJsonObject.get("name").asString
            val imageUrl = categoryJson.asJsonObject.get("image_url").asString

            val category = Category(id, name, imageUrl)
            categories.add(category)
        }

        return Categories(categories)
    }
}
