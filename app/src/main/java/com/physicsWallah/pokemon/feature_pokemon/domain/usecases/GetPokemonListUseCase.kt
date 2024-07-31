package com.physicsWallah.pokemon.feature_pokemon.domain.usecases

import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.feature_pokemon.domain.repository.PokemonRepository
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(): Flow<Resource<List<PokemonModel>>> {
        return pokemonRepository.getPokemonList()
    }
}