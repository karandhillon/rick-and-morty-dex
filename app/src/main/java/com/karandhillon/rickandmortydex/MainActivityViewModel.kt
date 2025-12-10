package com.karandhillon.rickandmortydex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.karandhillon.rickandmortydex.network.Character
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InvalidClassException

class MainActivityViewModel(
    private val rickAndMortyApiService: RickAndMortyApiService,
): ViewModel() {
    sealed interface MainActivityUiState {
        data object Loading: MainActivityUiState
        data class Success(val characters: List<Character>): MainActivityUiState
        data class Error(val message: String): MainActivityUiState
    }

    private val _mainActivityUiState: MutableStateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState.Loading)
    val mainActivityUiState: StateFlow<MainActivityUiState> = _mainActivityUiState

    fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characterListResponse = rickAndMortyApiService.getAllCharacters()

                _mainActivityUiState.value = MainActivityUiState.Success(characters = characterListResponse.results)
            } catch (e: Exception) {
                _mainActivityUiState.value = MainActivityUiState.Error(message = "Error occurred: ${e.message}")
            }
        }
    }

    companion object {
        fun getMainActivityViewModelFactory(
            rickAndMortyApiService: RickAndMortyApiService,
        ): ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAS")
                        return MainActivityViewModel(
                            rickAndMortyApiService
                        ) as T
                    }

                    throw InvalidClassException("Invalid modelClass: ${modelClass.name}")
                }
            }
        }
    }
}