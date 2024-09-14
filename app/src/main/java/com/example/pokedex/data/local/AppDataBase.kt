package com.example.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePokemon::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDAO
}