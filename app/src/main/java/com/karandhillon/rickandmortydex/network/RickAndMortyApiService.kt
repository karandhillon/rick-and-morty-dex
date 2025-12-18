package com.karandhillon.rickandmortydex.network

import com.karandhillon.rickandmortydex.network.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterListResponse>
}
