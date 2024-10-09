package com.example.pokedex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.util.Resource
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface FavoritePokemonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePokemon(pokemon: Pokemon)

    @Delete
    fun deleteFavoritePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM favorite_pokemon")
    fun getAllFavoritePokemon(): List<Pokemon>

    @Query("SELECT * FROM favorite_pokemon WHERE name = :name")
    fun getPokemonByName(name: String): Pokemon
}