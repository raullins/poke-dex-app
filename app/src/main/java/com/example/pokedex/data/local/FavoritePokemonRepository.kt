package com.example.pokedex.data.local

import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class FavoritePokemonRepository @Inject constructor(
    private val favoritePokemonDao: FavoritePokemonDAO
) {

    fun addFavoritePokemon(pokemon: Pokemon) {
        favoritePokemonDao.insertFavoritePokemon(pokemon)
    }

    fun removeFavoritePokemon(pokemon: Pokemon) {
        favoritePokemonDao.deleteFavoritePokemon(pokemon)
    }

    fun getAllFavoritePokemons(): List<Pokemon> = favoritePokemonDao.getAllFavoritePokemon()

    fun getPokemonByName(name: String): Pokemon =
        favoritePokemonDao.getPokemonByName(name);
}