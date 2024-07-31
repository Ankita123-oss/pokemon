package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.physicsWallah.pokemon.R
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList.NoNetworkComposable
import com.physicsWallah.pokemon.ui.theme.PokemonRed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsComposable(
    pokemonDetailsViewModel: PokemonDetailsViewmodel = hiltViewModel(),
    pokemonModel: PokemonModel,
    navigateBack: () -> Unit
) {
    val detailsDataState = pokemonDetailsViewModel.detailsDataState.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    detailsDataState.detailsData.name?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(start = 30.dp),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokemonRed,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },

        ) { innerPadding ->

        val isNetworkAvailable = pokemonDetailsViewModel.isNetworkAvailable.value

        if (isNetworkAvailable) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                if (detailsDataState.isLoading.not()) {
                    PokemonDetailsCard(detailsDataState.detailsData)
                }
            }
        } else {
            NoNetworkComposable(onRetryClick = {
                pokemonDetailsViewModel.getPokemonDetails(pokemonModel.url.orEmpty())
            })
        }

        if (detailsDataState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }


    LaunchedEffect(Unit) {
        pokemonDetailsViewModel.getPokemonDetails(pokemonModel.url.orEmpty())
    }

}

@Composable
fun PokemonDetailsCard(details: PokemonDetailModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            details.sprites?.front_default?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = details.name,
                    modifier = Modifier
                        .size(400.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Text(
                text = details.name.orEmpty(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(R.string.height_dm, details.height ?: 0),
                )
                Text(
                    text = stringResource(R.string.weight_kg, details.weight ?: 0),
                )
            }
        }
    }
}


