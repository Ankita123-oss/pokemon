package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonDetails

import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel

data class PokemonDetailsState(
    val detailsData: PokemonDetailModel = PokemonDetailModel(),
    val isLoading: Boolean = false,
    val error: String? = null
)