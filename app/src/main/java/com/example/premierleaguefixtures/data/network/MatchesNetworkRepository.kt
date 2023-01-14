package com.example.premierleaguefixtures.data.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.premierleaguefixtures.data.MatchDatabase
import com.example.premierleaguefixtures.data.model.Match
import com.example.premierleaguefixtures.data.model.toMatchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MatchesNetworkRepository @Inject constructor(
    private val matchesService: MatchesService,
    private val database: MatchDatabase?,
    private val context: Context
) {

    fun getMatches(): Flow<List<Match>> = flow {
        try {
            val response = matchesService.matches()
            if (response.isSuccessful) {
                val matchesFromServer = response.body()
                if (matchesFromServer != null) {
                    emit(matchesFromServer)
                    database?.matchDao()?.clearTable()
                    database?.matchDao()?.insertAll(matchesFromServer.map { it.toMatchEntity() })
                }
            } else {
                Log.e("MatchesService", "Error! " + response.code().toString())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Error loading data. Check internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}