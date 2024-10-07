package com.example.pokedex.data.models

import com.example.pokedex.data.remote.responses.Ability
import com.example.pokedex.data.remote.responses.Stat

data class PokedexListEntry(
    val pokemonName: String,
    val imageUrl: String,
    val spriteFrontDefault: String,
    val number: Int,
    val types: List<String>,
    val weight: Int,
    val height: Int,
    val statsNames: List<String>,
    val statsValues: List<Int>,
    val abilities: List<String>
)