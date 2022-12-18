package com.example.premierleaguefixtures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.premierleaguefixtures.screens.MainScreen
import com.example.premierleaguefixtures.screens.MatchDetailsScreen.MatchDetailsScreen
import com.example.premierleaguefixtures.ui.theme.PremierLeagueFixturesTheme

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremierLeagueFixturesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MatchDetailsScreen()
                    MainScreen()
                }
            }
        }
    }
}