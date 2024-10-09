package com.example.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.data.remote.responses.Pokemon

@Database(entities = [Pokemon::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDAO
}