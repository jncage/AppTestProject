package com.example.apptestproject.utils

import android.util.Log
import com.example.apptestproject.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

class CategoriesDeserializer : JsonDeserializer<List<Category>> {
    private val _aTAG = this.javaClass.simpleName
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Category> {
        val categories = mutableListOf<Category>()

        json?.asJsonObject?.entrySet()?.forEach { entry ->
            val jsonArray = entry.value.asJsonArray
            Log.d(_aTAG, "jsonArray is $jsonArray")
            for (jsonElement in jsonArray) {
                val categoryObject = jsonElement.asJsonObject
                Log.d(_aTAG, "categoryObject is $categoryObject")
                val category = parseCategory(categoryObject)
                Log.d(_aTAG, "category is $category")
                categories.add(category)
            }
        }

        return categories
    }

    private fun parseCategory(categoryObject: JsonObject): Category {
        val categoryClass = Category::class.java
        val constructorParameters = categoryClass.kotlin.primaryConstructor?.parameters

        val args = constructorParameters?.associateWith { parameter ->
            val jsonParameterField = getJsonFieldName(parameter)
            val jsonValue = categoryObject.get(jsonParameterField)
            getPropertyValue(parameter.type.javaType, jsonValue)
        } ?: emptyMap()

        return categoryClass.kotlin.primaryConstructor?.callBy(args)
            ?: createEmptyCategory()
    }

    private fun createEmptyCategory(): Category {
        return Category(0, "", "")
    }

    private fun getPropertyValue(type: Type, jsonValue: JsonElement?): Any? {
        return when (type) {
            Int::class.java -> jsonValue?.asInt
            String::class.java -> jsonValue?.asString ?: ""
            else -> null
        }
    }

    private fun getJsonFieldName(parameter: KParameter): String {
        return when (parameter.name) {
            "imageUrl" -> "image_url"
            else -> parameter.name!!
        }
    }






}




