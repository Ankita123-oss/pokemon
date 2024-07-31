package com.physicsWallah.pokemon.feature_pokemon.di

import com.physicsWallah.pokemon.feature_pokemon.data.repository.PokemonApiService
import com.physicsWallah.pokemon.feature_pokemon.data.repository.PokemonRepositoryImpl
import com.physicsWallah.pokemon.feature_pokemon.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Provides
    fun providesPokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    fun providesPokemonRepository(pokemonApiService: PokemonApiService) : PokemonRepository {
        return PokemonRepositoryImpl(pokemonApiService)
    }

}