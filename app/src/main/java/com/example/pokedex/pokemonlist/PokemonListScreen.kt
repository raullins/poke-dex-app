package com.example.pokedex.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    val searchText = remember { mutableStateOf("") }
    val pokemonList by viewModel.filteredPokemonList

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pokemon_logo),
                contentDescription = "Pokemon logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            SearchBar(
                hint = "Procurar...",
                text = viewModel.searchText.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearch = { query ->
                    viewModel.filterPokemonListByNamePrefix(query)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PokemonList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit,
    text: String
) {

    val isHintDisplayed = text.isEmpty()

    Box(modifier = modifier) {

        BasicTextField(
            value = text,

            onValueChange = {
                onSearch(it)
            },

            maxLines = 1,

            singleLine = true,

            textStyle = TextStyle(color = Color.Black),

            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged { focusState ->
                    isHintDisplayed == !focusState.isFocused && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            )
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),
//    pokemonList: List<PokedexListEntry>
) {

    val pokemonList by remember {
        viewModel.filteredPokemonList
    }

    val endReached by remember {
        viewModel.endReached
    }

    val loadError by remember {
        viewModel.loadError
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {

        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }

        items(itemCount) { index ->
            // Carregue mais Pokémon quando atingir o final da lista e a pesquisa não estiver ativa
            if (index >= itemCount - 1 && !viewModel.endReached.value && viewModel.filteredPokemonList.value.size == viewModel.pokemonList.size) {
                // Certifique-se de que apenas carrega mais quando não há filtro aplicado
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = index, entries = pokemonList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface

    // State para armazenar a cor dominante
    var dominantColor by remember { mutableStateOf(Color.Transparent) }

    // Obtenha o contexto
    val context = LocalContext.current

    // Crie um ImageRequest separado para processamento da cor dominante
    LaunchedEffect(entry.imageUrl) {
        val request = ImageRequest.Builder(context)
            .data(entry.imageUrl)
            .allowHardware(false) // Necessário para operações de Bitmap
            .build()

        val drawable = request.context.imageLoader.execute(request).drawable
        drawable?.let {
            // Calcular a cor dominante
            viewModel.calculateDominantColor(it) { color ->
                dominantColor = color
            }
        }
    }

    val isFavorite = viewModel.isPokemonFavorite(entry.number)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = entry.imageUrl,
                contentDescription = entry.pokemonName,
                modifier = modifier
                    .size(120.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )

            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 1.dp)
            )

            IconButton(
                onClick = {
                    if (isFavorite) {
                        viewModel.removePokemonFromFavorites(
                            FavoritePokemon(
                                entry.number,
                                entry.pokemonName,
                                entry.imageUrl
                            )
                        )
                    } else {
                        viewModel.addPokemonToFavorites(
                            FavoritePokemon(
                                entry.number,
                                entry.pokemonName,
                                entry.imageUrl
                            )
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar Pokémon",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

        }
    }
}

@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokedexEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            if (entries.size >= rowIndex * 2 + 2) {
                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = "Tentar novamente")
        }
    }
}