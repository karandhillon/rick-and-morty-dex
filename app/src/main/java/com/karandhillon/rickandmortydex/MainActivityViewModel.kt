package com.karandhillon.rickandmortydex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import com.karandhillon.rickandmortydex.network.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val rickAndMortyApiService: RickAndMortyApiService,
) : ViewModel() {
    private val _characters: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = rickAndMortyApiService.getAllCharacters()

            if (response.isSuccessful) {
                response.body()?.let { characterListResponse ->
                    _characters.value = characterListResponse.results
                }
            }
        }
    }
}
