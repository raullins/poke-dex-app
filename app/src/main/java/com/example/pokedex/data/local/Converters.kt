package com.example.pokedex.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pokedex.data.remote.responses.Ability
import com.example.pokedex.data.remote.responses.Cries
import com.example.pokedex.data.remote.responses.Form
import com.example.pokedex.data.remote.responses.GameIndice
import com.example.pokedex.data.remote.responses.Move
import com.example.pokedex.data.remote.responses.Species
import com.example.pokedex.data.remote.responses.Sprites
import com.example.pokedex.data.remote.responses.Stat
import com.example.pokedex.data.remote.responses.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Acabei não utilizando esta ideia.
//@ProvidedTypeConverter
class Converters {

    // Converters para listas
    @TypeConverter
    fun fromAbilityList(abilities: List<Ability>?): String {
        return Gson().toJson(abilities)
    }

    @TypeConverter
    fun toAbilityList(abilitiesString: String): List<Ability> {
        val type = object : TypeToken<List<Ability>>() {}.type
        return Gson().fromJson(abilitiesString, type)
    }

    @TypeConverter
    fun fromFormList(forms: List<Form>?): String {
        return Gson().toJson(forms)
    }

    @TypeConverter
    fun toFormList(formsString: String): List<Form> {
        val type = object : TypeToken<List<Form>>() {}.type
        return Gson().fromJson(formsString, type)
    }

    @TypeConverter
    fun fromGameIndiceList(gameIndices: List<GameIndice>?): String {
        return Gson().toJson(gameIndices)
    }

    @TypeConverter
    fun toGameIndiceList(gameIndicesString: String): List<GameIndice> {
        val type = object : TypeToken<List<GameIndice>>() {}.type
        return Gson().fromJson(gameIndicesString, type)
    }

    @TypeConverter
    fun fromMoveList(moves: List<Move>?): String {
        return Gson().toJson(moves)
    }

    @TypeConverter
    fun toMoveList(movesString: String): List<Move> {
        val type = object : TypeToken<List<Move>>() {}.type
        return Gson().fromJson(movesString, type)
    }

    @TypeConverter
    fun fromStatList(stats: List<Stat>?): String {
        return Gson().toJson(stats)
    }

    @TypeConverter
    fun toStatList(statsString: String): List<Stat> {
        val type = object : TypeToken<List<Stat>>() {}.type
        return Gson().fromJson(statsString, type)
    }

    @TypeConverter
    fun fromTypeList(types: List<Type>?): String {
        return Gson().toJson(types)
    }

    @TypeConverter
    fun toTypeList(typesString: String): List<Type> {
        val type = object : TypeToken<List<Type>>() {}.type
        return Gson().fromJson(typesString, type)
    }

    // Converter para objetos complexos
    @TypeConverter
    fun fromCries(cries: Cries): String {
        return Gson().toJson(cries)
    }

    @TypeConverter
    fun toCries(criesString: String): Cries {
        return Gson().fromJson(criesString, Cries::class.java)
    }

    @TypeConverter
    fun fromSpecies(species: Species): String {
        return Gson().toJson(species)
    }

    @TypeConverter
    fun toSpecies(speciesString: String): Species {
        return Gson().fromJson(speciesString, Species::class.java)
    }

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun toSprites(spritesString: String): Sprites {
        return Gson().fromJson(spritesString, Sprites::class.java)
    }

    // Converter para `List<Any>` para String (JSON) para past_abilities
    @TypeConverter
    fun fromPastAbilities(value: List<Any>?): String {
        return Gson().toJson(value)
    }

    // Converter de String (JSON) para `List<Any>` para past_abilities
    @TypeConverter
    fun toPastAbilities(value: String): List<Any>? {
        val listType = object : TypeToken<List<Any>>() {}.type
        return Gson().fromJson(value, listType)
    }

}