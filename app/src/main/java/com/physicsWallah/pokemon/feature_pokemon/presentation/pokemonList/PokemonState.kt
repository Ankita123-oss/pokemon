package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList

import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel

data class PokemonState(
    val pokemon: List<PokemonModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null)