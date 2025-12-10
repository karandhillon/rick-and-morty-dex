package com.karandhillon.rickandmortydex.network

data class CharacterListResponse(
    val info: PageInfo,
    val results: List<Character>,
)