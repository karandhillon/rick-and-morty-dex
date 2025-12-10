package com.karandhillon.rickandmortydex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val retrofit =
            createRetrofit(
                baseUrl = "https://rickandmortyapi.com/api/",
                converterFactory = GsonConverterFactory.create(),
            )
        val rickAndMortyApiService = retrofit.create(RickAndMortyApiService::class.java)
        val characterListViewModel: CharacterListViewModel by viewModels {
            viewModelFactory {
                initializer {
                    CharacterListViewModel(rickAndMortyApiService)
                }
            }
        }

        characterListViewModel.fetchCharacters()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    val state = characterListViewModel.characterListUiState.collectAsState()

                    when (state.value) {
                        is CharacterListViewModel.CharacterListUiState.Loading -> {
                            Text("Loading, please wait!")
                        }

                        is CharacterListViewModel.CharacterListUiState.Success -> {
                            LazyColumn {
                                items(items = (state.value as CharacterListViewModel.CharacterListUiState.Success).characters) {
                                    Text(it.name)
                                }
                            }
                        }

                        is CharacterListViewModel.CharacterListUiState.Error -> {
                            Text("Some error occurred: ${(state.value as CharacterListViewModel.CharacterListUiState.Error).message}")
                        }
                    }
                }
            }
        }
    }

    private fun createRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
}
