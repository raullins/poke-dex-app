package com.example.pokedex.repository

import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.local.FavoritePokemonDAO
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class FavoritePokemonRepository @Inject constructor(
    private val favoritePokemonDao: FavoritePokemonDAO
) {

    fun addFavoritePokemon(pokemon: FavoritePokemon) {
        favoritePokemonDao.insertFavoritePokemon(pokemon)
    }

    fun removeFavoritePokemon(pokemon: FavoritePokemon) {
        favoritePokemonDao.deleteFavoritePokemon(pokemon)
    }

    fun getAllFavoritePokemons(): Flow<List<FavoritePokemon>> = favoritePokemonDao.getAllFavoritePokemon()

    // Função para obter um Pokémon favorito pelo nome, envolvendo o resultado em um Resource
    fun getFavoritePokemonByName(name: String): Resource<FavoritePokemon> {
        return try {
            val pokemon = favoritePokemonDao.getFavoriteByName(name)
            if (pokemon != null) {
                Resource.Success(pokemon)
            } else {
                Resource.Error("Pokémon não encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Erro ao buscar Pokémon", null)
        }
    }
}