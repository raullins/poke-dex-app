package com.example.pokedex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.util.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePokemon(pokemon: FavoritePokemon)

    @Delete
    fun deleteFavoritePokemon(pokemon: FavoritePokemon)

    @Query("SELECT * FROM favorite_pokemon")
    fun getAllFavoritePokemon(): Flow<List<FavoritePokemon>>

    @Query("SELECT * FROM favorite_pokemon WHERE pokemonName = :name")
    fun getFavoriteByName(name: String): FavoritePokemon
}