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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import com.karandhillon.rickandmortydex.ui.theme.RickAndMortyDexTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val rickAndMortyApiService = retrofit.create(RickAndMortyApiService::class.java)
        val viewModelProvider: ViewModelProvider = ViewModelProvider(
            owner = this,
            factory = MainActivityViewModel.getMainActivityViewModelFactory(rickAndMortyApiService)
        )
        val mainActivityViewModel: MainActivityViewModel = viewModelProvider[MainActivityViewModel::class]

        mainActivityViewModel.fetchCharacters()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)
                    ) {
                        val state = mainActivityViewModel.mainActivityUiState.collectAsState()

                        when (state.value) {
                            is MainActivityViewModel.MainActivityUiState.Loading -> {
                                Text("Loading, please wait!")
                            }
                            is MainActivityViewModel.MainActivityUiState.Success -> {
                                LazyColumn {
                                    items(items = (state.value as MainActivityViewModel.MainActivityUiState.Success).characters) {
                                        Text(it.name)
                                    }
                                }
                            }
                            is MainActivityViewModel.MainActivityUiState.Error -> {
                                Text("Some error occurred: ${(state.value as MainActivityViewModel.MainActivityUiState.Error).message}")
                            }
                        }
                    }
                }
            }
        }
}