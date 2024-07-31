package com.physicsWallah.pokemon.feature_pokemon.data.repository

import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.feature_pokemon.domain.repository.PokemonRepository
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokemonApiService: PokemonApiService) :
    PokemonRepository {
    override fun getPokemonList(): Flow<Resource<List<PokemonModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = pokemonApiService.getPokemonList()
            if (response.isSuccessful) {

                if (response.body() != null) {
                    emit(
                        Resource.Success(response.body()!!.toPokemonList()!!))
                } else {
                    emit(Resource.Error(response.errorBody()?.string().orEmpty(), null))
                }

            } else {
                emit(Resource.Error(response.errorBody()?.string().orEmpty(), null))
            }
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Timeout exception occurred"))
        } catch (e: HttpException) {
            emit(Resource.Error("API Exception"))
        } catch (e: IOException) {
            emit(Resource.Error("No Internet"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error"))
        }
    }

    override fun getPokemonDetails(url: String): Flow<Resource<PokemonDetailModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = pokemonApiService.getPokemonDetail(url)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toPokemonDetailModel()!!))
            } else {
                emit(Resource.Error(response.errorBody()?.string().orEmpty(), null))
            }
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Timeout exception occurred"))
        } catch (e: HttpException) {
            emit(Resource.Error("API Exception"))
        } catch (e: IOException) {
            emit(Resource.Error("No Internet"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error"))
        }
    }
}