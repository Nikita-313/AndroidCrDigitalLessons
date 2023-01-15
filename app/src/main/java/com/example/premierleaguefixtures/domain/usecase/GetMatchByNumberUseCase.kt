package com.example.premierleaguefixtures.domain.usecase

import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchByNumberUseCase @Inject constructor(
    private val localMatchesRepository: LocalMatchesRepository
) {
    fun execute(number : Int) : Flow<FootballMatch> {
        return localMatchesRepository.getMatchByNumber(number)
    }
}