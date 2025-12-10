package com.karandhillon.rickandmortydex.network

import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterListResponse
}