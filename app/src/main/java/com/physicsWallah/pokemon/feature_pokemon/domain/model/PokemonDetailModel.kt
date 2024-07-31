package com.physicsWallah.pokemon.feature_pokemon.domain.model

data class PokemonDetailModel(
    val id: Int?=null,
    val name: String?=null,
    val height: Int?=null,
    val weight: Int?=null,
    val sprites: SpritesModel?=null,
)

data class SpritesModel(
    val front_default: String?=null
)