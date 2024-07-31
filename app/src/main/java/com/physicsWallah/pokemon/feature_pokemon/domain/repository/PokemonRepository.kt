package com.physicsWallah.pokemon.feature_pokemon.domain.repository

import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Resource<List<PokemonModel>>>

    fun getPokemonDetails(url: String): Flow<Resource<PokemonDetailModel>>

}