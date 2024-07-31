package com.physicsWallah.pokemon.navigation

sealed class NavigationRoute(val rootName: String) {
    data object PokemonListScreen: NavigationRoute("PokemonListScreen")
    data object PokemonDetailsScreen: NavigationRoute("PokemonDetailsScreen")

}