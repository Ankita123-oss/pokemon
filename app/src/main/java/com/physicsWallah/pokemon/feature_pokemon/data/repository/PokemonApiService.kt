package com.physicsWallah.pokemon.feature_pokemon.data.repository

import com.physicsWallah.pokemon.feature_pokemon.data.model.PokemonDto
import com.physicsWallah.pokemon.feature_pokemon.data.model.PokemonDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonDto>

    @GET
    suspend fun getPokemonDetail(@Url url: String): Response<PokemonDetailDto>

}