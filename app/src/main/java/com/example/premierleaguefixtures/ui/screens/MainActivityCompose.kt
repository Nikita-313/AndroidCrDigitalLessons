package com.example.premierleaguefixtures.ui.screens

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.premierleaguefixtures.data.local.MatchDatabase
import com.example.premierleaguefixtures.ui.theme.PremierLeagueFixturesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremierLeagueFixturesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main"){
                        composable("main"){ MainScreen(navController = navController) }
                        composable(route = "details/{matchNumber}",
                        arguments = listOf(
                            navArgument("matchNumber"){
                                type = NavType.IntType
                            }
                        )
                        ){ MatchDetailsScreen(navController = navController, matchNumber = it.arguments?.getInt("matchNumber")) }
                    }
                }
            }
        }
    }
}