package com.example.premierleaguefixtures.domain.usecase

import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedMatchesUseCase @Inject constructor(
    private val localMatchesRepository: LocalMatchesRepository
) {
    suspend fun execute(): Flow<List<FootballMatch>> {
        return localMatchesRepository.getMatches()
    }
}