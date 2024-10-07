package com.example.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.data.local.converters.Converter

@Database(entities = [FavoritePokemon::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDAO
}