package com.example.marvelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvelapp.ui.screens.CharacterDetailScreen
import com.example.marvelapp.ui.screens.CharacterListScreen
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.ui.viewmodel.MarvelViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                MarvelApp()
            }
        }
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characterList") {
        composable("characterList") {
            CharacterListScreen(
                viewModel = viewModel,
                onCharacterClick = { character ->
                    navController.navigate("characterDetail/${character.id}")
                }
            )
        }
        composable(
            route = "characterDetail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            val character = viewModel.characters.value.find { it.id == characterId }
            character?.let {
                CharacterDetailScreen(
                    character = it,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}