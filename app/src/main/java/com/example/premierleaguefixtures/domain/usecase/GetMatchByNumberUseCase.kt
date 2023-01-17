package com.example.premierleaguefixtures.domain.usecase

import android.util.Log
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMatchByNumberUseCase @Inject constructor(
    private val localMatchesRepository: LocalMatchesRepository
) {
    fun execute(number: Int): Flow<FootballMatch> {
        return localMatchesRepository.getMatchByNumber(number)
            .catch {
                Log.e("LoadMatches", it.message.toString())
            }
    }
}