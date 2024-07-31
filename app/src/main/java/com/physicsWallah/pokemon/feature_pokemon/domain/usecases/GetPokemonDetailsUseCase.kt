package com.physicsWallah.pokemon.feature_pokemon.domain.usecases

import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.feature_pokemon.domain.repository.PokemonRepository
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject  constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(url:String): Flow<Resource<PokemonDetailModel>> {
        return pokemonRepository.getPokemonDetails(url)
    }
}