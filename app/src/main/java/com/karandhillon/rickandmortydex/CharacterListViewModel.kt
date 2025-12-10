package com.karandhillon.rickandmortydex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import com.karandhillon.rickandmortydex.network.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val rickAndMortyApiService: RickAndMortyApiService,
) : ViewModel() {
    sealed interface CharacterListUiState {
        data object Loading : CharacterListUiState

        data class Success(
            val characters: List<Character>,
        ) : CharacterListUiState

        data class Error(
            val message: String,
        ) : CharacterListUiState
    }

    private val _characterListUiState: MutableStateFlow<CharacterListUiState> = MutableStateFlow(CharacterListUiState.Loading)
    val characterListUiState: StateFlow<CharacterListUiState> = _characterListUiState

    fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characterListResponse = rickAndMortyApiService.getAllCharacters()

                _characterListUiState.value = CharacterListUiState.Success(characters = characterListResponse.results)
            } catch (e: Exception) {
                _characterListUiState.value = CharacterListUiState.Error(message = "Error occurred: ${e.message}")
            }
        }
    }
}
