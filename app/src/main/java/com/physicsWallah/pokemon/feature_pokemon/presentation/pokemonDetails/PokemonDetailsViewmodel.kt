package com.physicsWallah.pokemon.feature_pokemon.presentation.pokemonDetails

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.physicsWallah.pokemon.core.Resource
import com.physicsWallah.pokemon.feature_pokemon.domain.model.PokemonDetailModel
import com.physicsWallah.pokemon.feature_pokemon.domain.usecases.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewmodel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _detailsDataState = mutableStateOf(PokemonDetailsState())
    val detailsDataState: State<PokemonDetailsState> = _detailsDataState

    private val _isNetworkAvailable: MutableState<Boolean> = mutableStateOf(true)
    val isNetworkAvailable: State<Boolean> = _isNetworkAvailable


    fun getPokemonDetails(url: String) {
        viewModelScope.launch {
            getPokemonDetailsUseCase.invoke(url).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _detailsDataState.value = PokemonDetailsState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _detailsDataState.value =
                            PokemonDetailsState(
                                isLoading = false,
                                detailsData = result.data ?: PokemonDetailModel()
                            )
                    }

                    is Resource.Error -> {
                        _detailsDataState.value =
                            PokemonDetailsState(isLoading = false, error = result.message)

                    }
                }
            }.launchIn(this)
        }
    }
}