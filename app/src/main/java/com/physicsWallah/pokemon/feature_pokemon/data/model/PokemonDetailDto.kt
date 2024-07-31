package com.physicsWallah.pokemon.feature_pokemon.data.model

import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import com.physicsWallah.pokemon.feature_pokemon.domain.model.SpritesModel

data class PokemonDetailDto(
    val id: Int? = null,
    val name: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val sprites: Sprites? = null,
) {
    fun toPokemonDetailModel(): PokemonDetailModel {
        return PokemonDetailModel(
            id = id, name = name?.replaceFirstChar(Char::titlecase),
            height = height, weight = weight, sprites = sprites?.toSpritesModel()
        )
    }
}

data class Sprites(
    val front_default: String? = null,
) {
    fun toSpritesModel(): SpritesModel {
        return SpritesModel(front_default)
    }
}