package com.example.apptestproject.utils

import com.example.apptestproject.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class CategoriesDeserializer : JsonDeserializer<List<Category>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Category> {
        val categoryList = mutableListOf<Category>()
        if ((json != null) && json.isJsonObject) {
            val jsonObject = json.asJsonObject

            for ((_, categoryElement) in jsonObject.entrySet()) {
                if (categoryElement.isJsonArray) {
                    val categoriesArray = categoryElement.asJsonArray
                    for (categoryObject in categoriesArray) {
                        if (categoryObject.isJsonObject) {
                            val category = parseCategory(categoryObject.asJsonObject)
                            categoryList.add(category)
                        }
                    }
                }
            }
        }
        return categoryList
    }

    private fun parseCategory(categoryObject: JsonObject): Category {
        val categoryClass = Category::class.java
        val categoryConstructor = categoryClass.getConstructor()
        val category = categoryConstructor.newInstance()

        for (property in categoryClass.declaredFields) {
            property.isAccessible = true
            val jsonValue = categoryObject.get(property.name)
            if (jsonValue != null) {
                when (property.type) {
                    Int::class.java -> property.set(category, jsonValue.asInt)
                    String::class.java -> property.set(category, jsonValue.asString)
                }
            }
        }
        return category
    }
}