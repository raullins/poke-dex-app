package com.example.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.data.remote.responses.Ability
import com.example.pokedex.data.remote.responses.Stat

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemon(
    @PrimaryKey val number: Int,
    val pokemonName: String,
    val imageUrl: String,
    val spriteFrontDefault: String,
    val types: List<String>,
    val weight: Int,
    val height: Int,
    val statsNames: List<String>,
    val statsValues: List<Int>,
    val abilities: List<String>
)