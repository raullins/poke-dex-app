package com.example.pokedex.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pokedex.data.remote.responses.Sprites
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Acabei não utilizando esta ideia.
@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun toSprites(spritesString: String): Sprites {
        val type = object : TypeToken<Sprites>() {}.type
        return Gson().fromJson(spritesString, type)
    }
}