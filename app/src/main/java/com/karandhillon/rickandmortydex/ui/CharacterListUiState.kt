package com.karandhillon.rickandmortydex.ui

import com.karandhillon.rickandmortydex.network.model.Character

sealed interface CharacterListUiState {
    data object Loading : CharacterListUiState

    data class Success(
        val characters: List<Character>,
    ) : CharacterListUiState

    data class Error(
        val message: String,
    ) : CharacterListUiState
}
