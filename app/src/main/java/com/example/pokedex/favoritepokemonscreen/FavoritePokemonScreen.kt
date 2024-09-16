package com.example.pokedex.favoritepokemonscreen

import android.widget.Toast
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.colorResource
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
import com.example.pokedex.pokemonlist.PokedexRow
import com.example.pokedex.pokemonlist.PokemonListViewModel
import com.example.pokedex.ui.theme.RobotoCondensed

@Composable
fun FavoritePokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

//    val searchText = remember { mutableStateOf("") }
//    val pokemonList by viewModel.filteredPokemonList
//
//    // Observe a lista de favoritos como um estado
//    val favoritePokemonList by viewModel.favoritePokemonList.collectAsState()

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

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Usar ícone padrão para evitar problemas com espelhamento
                        contentDescription = "Voltar",
                        tint = Color.White, // Define a cor branca diretamente
                        modifier = Modifier
                            .size(52.dp)
                            //.offset(16.dp, 16.dp) // Define o deslocamento do ícone
                            .clickable {
                                navController.popBackStack() // Ação de clique para voltar
                            }
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                SearchBar(
                    hint = "Procurar...",
                    text = viewModel.searchText.value,
                    modifier = Modifier.weight(1f).height(42.dp), // Fazer a SearchBar ocupar o máximo de espaço
                    onSearch = { query ->
                        viewModel.filterFavoritePokemonList(query)
                    }
                )

//                Spacer(modifier = Modifier.width(8.dp)) // Espaçamento entre o botão e a barra de pesquisa
//
//                Button(
//                    onClick = {
//                        navController.navigate("favorite_pokemons_screen")
//                    },
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .height(42.dp)
//                ) {
//                    Text(text = "Favoritos")
//                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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

// Observar a lista de favoritos usando collectAsState
    val favoritePokemonList by viewModel.getFavoritePokemonAsPokedexListEntries()
        .collectAsState(initial = emptyList())

//    val endReached by remember {
//        viewModel.endReached
//    }
//
//    val loadError by remember {
//        viewModel.loadError
//    }
//
//    val isLoading by remember {
//        viewModel.isLoading
//    }

    if (favoritePokemonList.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Nenhum Pokémon favorito encontrado!")
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            val itemCount = if (favoritePokemonList.size % 2 == 0) {
                favoritePokemonList.size / 2
            } else {
                favoritePokemonList.size / 2 + 1
            }

            items(itemCount) { index ->
                PokedexRow(
                    rowIndex = index,
                    entries = favoritePokemonList,
                    navController = navController
                )
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

    // Observe o estado reativo do favorito
    var isFavorite by remember { mutableStateOf(viewModel.isPokemonFavorite(entry.number)) }


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

            IconToggleButton(
                checked = isFavorite,
                onCheckedChange = {
                    if (isFavorite) {
                        viewModel.removePokemonFromFavorites(
                            FavoritePokemon(
                                entry.number,
                                entry.pokemonName,
                                entry.imageUrl
                            )
                        )
                        isFavorite = !isFavorite
                        Toast.makeText(
                            context,
                            "${entry.pokemonName} foi desfavoritado(a)!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.addPokemonToFavorites(
                            FavoritePokemon(
                                entry.number,
                                entry.pokemonName,
                                entry.imageUrl
                            )
                        )
                        isFavorite = !isFavorite
                        Toast.makeText(
                            context,
                            "${entry.pokemonName} foi favoritado(a)!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar Pokémon",
                    tint = if (isFavorite) colorResource(id = R.color.red) else colorResource(id = R.color.gray)
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