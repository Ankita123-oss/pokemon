package com.physicsWallah.pokemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonDetails.PokemonDetailsComposable
import com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList.PokemonListComposable
import com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList.getData
import com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList.setData

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.PokemonListScreen.rootName
    ) {
        composable(route = NavigationRoute.PokemonListScreen.rootName) {
            PokemonListComposable(
                navigateToPokemonDetailsScreen = {
                    navController.setData("pokemonDetails", it)
                    navController.navigate(NavigationRoute.PokemonDetailsScreen.rootName)
                })
        }
        composable(route = NavigationRoute.PokemonDetailsScreen.rootName) {
            navController.getData<PokemonModel>("pokemonDetails").let {
                if (it != null) {
                    PokemonDetailsComposable(pokemonModel = it, navigateBack = {
                        navController.navigateUp()
                    })
                }
            }
        }
    }
}