package com.example.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.repository.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Constants.PAGE_SIZE
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val favoritePokemonRepository: FavoritePokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableListOf<PokedexListEntry>()
    val filteredPokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())

    val _favoritePokemonList = MutableStateFlow<List<FavoritePokemon>>(emptyList())
    private var favoritePokemonList: StateFlow<List<FavoritePokemon>> =
        _favoritePokemonList // Assume que você já tem isso
    val favoritePokemonFilteredList = mutableStateOf<List<FavoritePokemon>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    var searchText = mutableStateOf("")

    init {
        loadPokemonPaginated()
        loadFavorites()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {

            isLoading.value = true

            val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)

            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }

                    currentPage++

                    loadError.value = ""
                    isLoading.value = false

                    pokemonList.addAll(pokedexEntries)
                    filteredPokemonList.value = pokemonList

                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }
        }
    }

    fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {

        val bitMap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitMap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun filterPokemonListByNamePrefix(prefix: String) {

        searchText.value = prefix

        if (prefix.isEmpty()) {
            filteredPokemonList.value = pokemonList
        } else {
            filteredPokemonList.value = pokemonList.filter {
                it.pokemonName.startsWith(prefix, ignoreCase = true)
            }
        }
    }

    fun filterFavoritePokemonList(prefix: String) {

        searchText.value = prefix

        if (prefix.isEmpty()) {
            favoritePokemonFilteredList.value = _favoritePokemonList.value
        } else {
            favoritePokemonFilteredList.value = _favoritePokemonList.value.filter {
                it.pokemonName.startsWith(prefix, ignoreCase = true)
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favoritePokemonRepository.getAllFavoritePokemons().collect {
                _favoritePokemonList.value = it
            }
        }
    }

    fun getFavoritePokemonAsPokedexListEntries(): StateFlow<List<PokedexListEntry>> {
        return favoritePokemonList.map { favoriteList ->
            favoriteList.map { favorite ->
                PokedexListEntry(
                    pokemonName = favorite.pokemonName,
                    imageUrl = favorite.imageUrl,
                    number = favorite.number
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    fun isPokemonFavorite(pokemonId: Int): Boolean {
        return _favoritePokemonList.value.any { it.number == pokemonId }
    }

    fun addPokemonToFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritePokemonRepository.addFavoritePokemon(pokemon)
                Log.d("Favorite", "Pokemon favoritado com sucesso: ${pokemon.pokemonName}")
            } catch (e: Exception) {
                Log.e("Favorite", "Erro ao favoritar o Pokémon", e)
            }
            //loadFavorites()
        }
    }

    fun removePokemonFromFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritePokemonRepository.removeFavoritePokemon(pokemon)
            //loadFavorites()
        }
    }
}