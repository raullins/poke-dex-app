package com.example.pokedex.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.local.AppDataBase
import com.example.pokedex.data.local.Converters
import com.example.pokedex.data.local.FavoritePokemonDAO
import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.repository.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "pokedex_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritePokemonDao(database: AppDataBase): FavoritePokemonDAO {
        return database.favoritePokemonDao()
    }

    @Provides
    @Singleton
    fun provideFavoritePokemonRepository(
        favoritePokemonDao: FavoritePokemonDAO
    ): FavoritePokemonRepository {
        return FavoritePokemonRepository(favoritePokemonDao)
    }
}