package com.example.pokedex.ui.pokemondetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.data.local.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val favoritePokemonRepository: FavoritePokemonRepository // Repositório de favoritos
) : ViewModel() {

    // Fluxo da lista de Pokémon favoritos
    private val _favoritePokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val favoritePokemonList: StateFlow<List<Pokemon>> = _favoritePokemonList

    init {
        getFavoritePokemons()
    }

    // Função para recuperar a lista de favoritos
    private fun getFavoritePokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritePokemonList.value = favoritePokemonRepository.getAllFavoritePokemons()
        }
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemon(pokemonName)
    }

    // Função para buscar um Pokémon específico pelo nome
    fun getFavoritePokemonByName(name: String): Pokemon? {
        return _favoritePokemonList.value.find { it.name == name }
    }
//
//    fun isPokemonFavorite(pokemonId: Int): Boolean {
//        return favoritePokemonList.value.any { it.number == pokemonId }
//    }
//
//    fun addPokemonToFavorites(pokemon: FavoritePokemon) {
//        viewModelScope.launch {
//            favoriteRepository.addFavoritePokemon(pokemon)
//        }
//    }
//
//    fun removePokemonFromFavorites(pokemon: FavoritePokemon) {
//        viewModelScope.launch {
//            favoriteRepository.removeFavoritePokemon(pokemon)
//        }
//    }
}