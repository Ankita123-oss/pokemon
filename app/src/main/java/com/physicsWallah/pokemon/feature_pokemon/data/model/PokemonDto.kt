package com.physicsWallah.pokemon.feature_pokemon.data.model

import androidx.compose.ui.text.capitalize
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import java.util.Locale


data class PokemonDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Result?>? = null,
) {
    fun toPokemonList(): List<PokemonModel>? {
        return results?.map {
            it?.toPokemonModel() ?: PokemonModel()
        }
    }
}

data class Result(
    val name: String? = null,
    val url: String? = null
) {
    fun toPokemonModel(): PokemonModel {
        return PokemonModel(name?.replaceFirstChar(Char::titlecase), url)
    }
}









