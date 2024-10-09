package com.example.pokedex.di

import android.app.Application
import androidx.room.Room
import com.example.pokedex.data.local.AppDataBase
import com.example.pokedex.data.local.Converters
import com.example.pokedex.data.local.FavoritePokemonDAO
import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.data.local.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    fun provideAppDataBase(
        application: Application
    ): AppDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = AppDataBase::class.java,
            name = "pokedex_database"
        )
//            .addTypeConverter(Converters::class)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoritePokemonDao(database: AppDataBase): FavoritePokemonDAO {
        return database.favoritePokemonDao()
    }

    @Provides
    fun provideFavoritePokemonRepository(
        favoritePokemonDao: FavoritePokemonDAO
    ): FavoritePokemonRepository {
        return FavoritePokemonRepository(favoritePokemonDao)
    }
}