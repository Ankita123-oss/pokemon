package com.physicsWallah.pokemon.feature_pokemon.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonModel(
    val name: String? = null,
    val url: String? = null
):Parcelable
