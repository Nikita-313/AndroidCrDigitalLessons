package com.example.premierleaguefixtures.domain.usecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadMatchesUseCase @Inject constructor(
    private val networkMatchesRepository: NetworkMatchesRepository,
    private val saveMatchesUseCase: SaveMatchesUseCase,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun execute(): Flow<List<FootballMatch>> {
        return networkMatchesRepository.getMatches()
            .onEach { saveMatchesUseCase.execute(it) }
            .flowOn(dispatcher)
            .catch {
                Toast.makeText(
                    context, "Error loading data. Check internet connection",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("LoadMatches", it.message.toString())
            }
    }
}
