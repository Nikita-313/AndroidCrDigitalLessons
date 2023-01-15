package com.example.premierleaguefixtures.domain.usecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadMatchesUseCase @Inject constructor(
    private val networkMatchesRepository: NetworkMatchesRepository,
    private val saveMatchesUseCase: SaveMatchesUseCase,
    private val context: Context
) {
    suspend fun execute(): List<FootballMatch> {
        var matchesFromServer: List<FootballMatch> = emptyList()
        try {
            networkMatchesRepository.getMatches().collect { response ->
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        matchesFromServer = response.body()!!
                        saveMatchesUseCase.execute(matchesFromServer)
                    }

                } else {
                    Log.e("LoadMatches", "Error! " + response.code().toString())
                }

            }
        } catch (e: java.lang.Exception){
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Error loading data. Check internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return matchesFromServer
    }
}
