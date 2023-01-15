package com.example.premierleaguefixtures.domain.usecase

import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMatchesByTeamNameUseCase @Inject constructor(
    private val localMatchesRepository: LocalMatchesRepository
) {
    fun execute(teamName: String): Flow<List<FootballMatch>> {
        return localMatchesRepository.searchMatchesByTeamName(teamName)
    }
}