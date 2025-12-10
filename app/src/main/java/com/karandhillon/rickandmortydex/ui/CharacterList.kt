package com.karandhillon.rickandmortydex.ui

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
fun CharacterList(
    characterList: List<Character>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(8.dp)) {
        items(items = characterList, key = { character -> character.id }) { character ->
            CharacterItem(character)
            Spacer(Modifier.padding(4.dp))
        }
    }
}
