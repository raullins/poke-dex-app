package com.example.pokedex.pokemondetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.repository.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
//    private val favoriteRepository: FavoritePokemonRepository // Repositório de favoritos
) : ViewModel() {

//    val favoritePokemonList = favoriteRepository.getAllFavoritePokemons()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemon(pokemonName)
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