package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.physicsWallah.pokemon.R
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.ui.theme.LightColor1
import com.physicsWallah.pokemon.ui.theme.LightColor2
import com.physicsWallah.pokemon.ui.theme.LightColor3
import com.physicsWallah.pokemon.ui.theme.LightColor4
import com.physicsWallah.pokemon.ui.theme.LightColor5
import com.physicsWallah.pokemon.ui.theme.LightColor6
import com.physicsWallah.pokemon.ui.theme.LightColor7
import com.physicsWallah.pokemon.ui.theme.PokemonRed
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListComposable(
    pokemonListViewModel: PokemonListViewmodel = hiltViewModel(),
    navigateToPokemonDetailsScreen: (PokemonModel) -> Unit
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        modifier = Modifier.size(150.dp, 80.dp),
                        painter = painterResource(id = R.drawable.pokemon_logo),
                        contentDescription = "pokemon_logo"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokemonRed,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },

        ) { innerPadding ->

        val isNetworkAvailable = pokemonListViewModel.isNetworkAvailable.value
        val pokemonListState = pokemonListViewModel.pokemonListState.value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isNetworkAvailable) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    itemsIndexed(
                        items = pokemonListState.pokemon ?: listOf()
                    ) { index, pokemonModel ->
                        PokemonCard(pokemonModel = pokemonModel, index = index, onItemClick = {
                            navigateToPokemonDetailsScreen.invoke(pokemonModel)
                        })
                    }
                }
            } else {
                NoNetworkComposable(onRetryClick = {
                    pokemonListViewModel.updateTheNetworkState()
                })
            }

            if (pokemonListState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }
}

val colorsArr = arrayOf(
    LightColor1,
    LightColor2,
    LightColor3,
    LightColor4,
    LightColor5,
    LightColor6,
    LightColor7
)

@Composable
fun PokemonCard(pokemonModel: PokemonModel, index: Int, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorsArr[index % colorsArr.count()]),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pokemonModel.name.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )

            Box(modifier = Modifier.padding(end = 20.dp)) {
                Image(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(id = R.drawable.poke_ball),
                    contentDescription = "poke_ball"
                )
            }
        }

    }
}

@Composable
fun NoNetworkComposable(onRetryClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextButton(onClick = onRetryClick, content = {
            Text(text = stringResource(R.string.no_network_available_please_check_and_try_again))
        })
    }
}


fun <T> NavHostController.setData(key: String, value: T) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavHostController.getData(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(key)
}