package com.karandhillon.rickandmortydex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import com.karandhillon.rickandmortydex.network.model.Character
import com.karandhillon.rickandmortydex.ui.CharacterListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val rickAndMortyApiService: RickAndMortyApiService,
) : ViewModel() {
    private val _characterListUiState: MutableStateFlow<CharacterListUiState> = MutableStateFlow(CharacterListUiState.Loading)
    val characterListUiState: StateFlow<CharacterListUiState> = _characterListUiState

    fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = rickAndMortyApiService.getAllCharacters()

                if (response.isSuccessful) {
                    response.body()?.let { characterListResponse ->
                        _characterListUiState.value = CharacterListUiState.Success(characters = characterListResponse.results)
                    } ?: {
                        _characterListUiState.value = CharacterListUiState.Error(message = "Empty Response")
                    }
                } else {
                    response.errorBody()?.let { response ->
                        _characterListUiState.value = CharacterListUiState.Error(message = response.toString())
                    }
                }
            } catch (e: Exception) {
                _characterListUiState.value = CharacterListUiState.Error(message = "Error occurred: ${e.message}")
            }
        }
    }
}
