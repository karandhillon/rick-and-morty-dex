package com.karandhillon.rickandmortydex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karandhillon.rickandmortydex.network.model.Character

@Composable
fun CharacterListScreen(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    onCharacterClick: (characterId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(8.dp)) {
            items(items = characters, key = { character -> character.id }) { character ->
                CharacterItem(
                    character,
                    onCharacterClick,
                )
                Spacer(Modifier.padding(4.dp))
            }
        }
    }
}