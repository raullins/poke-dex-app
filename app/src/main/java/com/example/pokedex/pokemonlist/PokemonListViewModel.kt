package com.example.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.repository.FavoritePokemonRepository
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Constants.PAGE_SIZE
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
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

    val favoritePokemonList = favoritePokemonRepository.getAllFavoritePokemons().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    var searchText = mutableStateOf("")

    init {
        loadPokemonPaginated()
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

    fun isPokemonFavorite(pokemonId: Int): Boolean {
        return favoritePokemonList.value.any { it.number == pokemonId }
    }

    fun addPokemonToFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritePokemonRepository.addFavoritePokemon(pokemon)
        }
    }

    fun removePokemonFromFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritePokemonRepository.removeFavoritePokemon(pokemon)
        }
    }
}