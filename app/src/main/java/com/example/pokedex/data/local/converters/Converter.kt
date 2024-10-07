package com.example.pokedex.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    // Converter de String (JSON) para List<String>
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Converter de List<String> para String (JSON)
    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    // Converter de String (JSON) para List<Int>
    @TypeConverter
    fun fromStringToIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Converter de List<Int> para String (JSON)
    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return Gson().toJson(list)
    }
}