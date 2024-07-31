package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonList

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.core.utils.isNetworkAvailable
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonModel
import com.physicsWallah.pokemon.feature_pokemon.domain.usecases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewmodel @Inject constructor(
    private val application: Application,
    private val getPokemonListUseCase: GetPokemonListUseCase,
) : AndroidViewModel(application) {

    private val _pokemonListState = mutableStateOf(PokemonState())
    val pokemonListState: State<PokemonState> = _pokemonListState

    private val _isNetworkAvailable: MutableState<Boolean> = mutableStateOf(true)
    val isNetworkAvailable: State<Boolean> = _isNetworkAvailable

    init {
        updateTheNetworkState()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase.invoke().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _pokemonListState.value = PokemonState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _pokemonListState.value =
                            PokemonState(
                                isLoading = false,
                                pokemon = result.data ?: listOf()
                            )
                    }

                    is Resource.Error -> {
                        _pokemonListState.value =
                            PokemonState(isLoading = false, error = result.message)

                    }
                }
            }.launchIn(this)
        }
    }


    fun updateTheNetworkState() {
        val isOnline = application.isNetworkAvailable()
        _isNetworkAvailable.value = isOnline

        if (isOnline) {
            getPokemonList()
        }
    }
}