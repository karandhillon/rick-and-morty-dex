package com.karandhillon.rickandmortydex

import CharacterDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karandhillon.rickandmortydex.network.RickAndMortyApiService
import com.karandhillon.rickandmortydex.ui.CharacterListScreen
import com.karandhillon.rickandmortydex.ui.theme.RickAndMortyDexTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainActivityViewModel: MainActivityViewModel by viewModels {
            viewModelFactory {
                initializer {
                    val rickAndMortyApiService = Retrofit
                        .Builder()
                        .baseUrl("https://rickandmortyapi.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(RickAndMortyApiService::class.java)

                    MainActivityViewModel(rickAndMortyApiService)
                }
            }
        }

        setContent {
            RickAndMortyDexTheme {
                val characters by mainActivityViewModel.characters.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "character_list"
                ) {
                    composable("character_list") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Column(modifier = Modifier.padding(innerPadding)) {
                                CharacterListScreen(
                                    characters,
                                    Modifier.padding(innerPadding),
                                    ) { characterId ->
                                    navController.navigate("character_details/$characterId")
                                }
                            }
                        }
                    }
                    composable("character_details/{characterId}") { backStackEntry ->
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Column(modifier = Modifier.padding(innerPadding)) {
                                val characterId = backStackEntry.arguments?.getString("characterId") ?: ""

                                characters.find { it.id == characterId.toInt() }?.let { character ->
                                    CharacterDetailScreen(character) {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}