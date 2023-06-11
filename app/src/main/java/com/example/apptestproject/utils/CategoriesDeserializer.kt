package com.example.apptestproject.utils

import com.example.apptestproject.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CategoriesDeserializer : JsonDeserializer<List<Category>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Category> {

        return json?.asJsonObject?.entrySet()?.flatMap { entry ->
            entry.value.asJsonArray.map { jsonElement ->
                val categoryObject = jsonElement.asJsonObject
                val id = categoryObject.get("id").asInt
                val name = categoryObject.get("name").asString
                val imageUrl = categoryObject.get("image_url").asString
                Category(id, name, imageUrl)
            }
        } ?: emptyList()

    }

}


