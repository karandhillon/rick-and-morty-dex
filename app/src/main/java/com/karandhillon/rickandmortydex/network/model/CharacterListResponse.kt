package com.karandhillon.rickandmortydex.network.model

data class CharacterListResponse(
    val info: PageInfo,
    val results: List<Character>,
)
