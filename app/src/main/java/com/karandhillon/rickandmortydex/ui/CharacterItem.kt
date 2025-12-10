package com.karandhillon.rickandmortydex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.karandhillon.rickandmortydex.network.model.Character
import com.karandhillon.rickandmortydex.network.model.LocationInfo
import kotlin.random.Random

@Composable
fun CharacterItem(character: Character) {
    val randomInt = rememberSaveable { Random.nextInt() }
    val color = Color(randomInt)

    Column(
        modifier =
            Modifier
                .background(color = color)
                .fillMaxWidth()
                .padding(7.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        CharacterName(character.name)
        CharacterProperty(property = "ID: ${character.id}")
        CharacterProperty(property = "Type: " + character.type)
        CharacterProperty(property = "Gender: " + character.gender)
        CharacterProperty(property = "Location: " + character.location)
        CharacterProperty(property = "Species: " + character.status)
        CharacterProperty(property = "Origin: " + character.origin.name)
    }
}

@Composable
fun CharacterName(
    characterName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Name: $characterName",
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
fun CharacterProperty(
    property: String,
    modifier: Modifier = Modifier,
) {
    Text(text = property, Modifier.padding(top = 4.dp, bottom = 4.dp))
}

@Preview
@Composable
fun CharacterItemPreview() {
    val character =
        Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "Human",
            gender = "Male",
            origin = LocationInfo("Earth", "url N/A"),
            location = LocationInfo("Earth", "url N/A"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = emptyList(),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:50:21.651Z",
        )

    CharacterItem(character)
}
