package com.example.pokedex.ui.pokemonlist

import android.widget.ImageView
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
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.imageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.local.FavoritePokemon
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

//    val searchText = remember { mutableStateOf("") }
//    val pokemonList by viewModel.filteredPokemonList
//
//    // Observe a lista de favoritos como um estado
//    val favoritePokemonList by viewModel.favoritePokemonList.collectAsState()

//    var isDarkTheme by remember { mutableStateOf(false) }

//    PokeDexTheme(darkTheme = isDarkTheme) {

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

//                Spacer(modifier = Modifier.width(8.dp))

//                IconButton(
//                    onClick = { isDarkTheme = !isDarkTheme },
//                    modifier = Modifier
//                        .size(50.dp)
//                        .align(Alignment.End)
//                        ,
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.dark_mode),
//                        contentDescription = "Mudar tema",
//                        tint = colorResource(id = R.color.yellow)
//                    )
//                }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    hint = "Procurar...",
                    text = viewModel.searchText.value,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp), // Fazer a SearchBar ocupar o máximo de espaço
                    onSearch = { query ->
                        viewModel.filterPokemonListByNamePrefix(query)
                    }
                )

                Spacer(modifier = Modifier.width(8.dp)) // Espaçamento entre o botão e a barra de pesquisa

                Button(
                    onClick = {
                        navController.navigate("favorite_pokemons_screen")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .height(42.dp)
                ) {
                    Text(
                        text = "Favoritos",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.yellow),
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            PokemonList(navController = navController)
        }
    }
//    }
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
//            AsyncImage(
//                model = entry.imageUrl,
//                contentDescription = entry.pokemonName,
//                modifier = modifier
//                    .size(120.dp)
//                    .align(alignment = Alignment.CenterHorizontally),
//            )

            GlideImage(
                imageUrl = entry.imageUrl,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .size(120.dp)
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

            val favoritePokemon = FavoritePokemon(
                entry.number,
                entry.pokemonName,
                entry.imageUrl,
                entry.spriteFrontDefault,
                entry.types,
                entry.weight,
                entry.height,
                entry.statsNames,
                entry.statsValues,
                entry.abilities
            )

            IconToggleButton(

                checked = isFavorite,
                onCheckedChange = {
                    if (isFavorite) {
                        viewModel.removePokemonFromFavorites(
                            favoritePokemon
                        )
                        isFavorite = !isFavorite
                        Toast.makeText(
                            context,
                            "${entry.pokemonName} foi desfavoritado(a)!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.addPokemonToFavorites(
                            favoritePokemon
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

@Composable
fun GlideImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // Usando AndroidView para integrar Glide com Compose
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            // Usar Glide para carregar a imagem
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image) // Placeholder enquanto carrega
                .error(R.drawable.error_image) // Imagem de erro caso falhe o carregamento
                .into(imageView)
        },
        modifier = modifier
            .size(120.dp) // Pode ajustar o tamanho conforme necessário
    )
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

//@Composable
//fun PokeDexTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    content: @Composable () -> Unit
//) {
//    val colors = if (darkTheme) {
//        darkColorScheme(
//            primary = Color(0xFFBB86FC),
//            primaryContainer = Color(0xFF3700B3),
//            secondary = Color(0xFF03DAC6)
//        )
//    } else {
//        lightColorScheme(
//            primary = Color(0xFF6200EE),
//            primaryContainer = Color(0xFF3700B3),
//            secondary = Color(0xFF03DAC6)
//        )
//    }
//
//    MaterialTheme(
//        colorScheme = colors,
////        typography = Typography,
//        content = content
//    )
//}