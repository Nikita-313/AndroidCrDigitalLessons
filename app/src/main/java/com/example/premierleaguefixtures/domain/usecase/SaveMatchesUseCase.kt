package com.example.premierleaguefixtures.domain.usecase

import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import javax.inject.Inject

class SaveMatchesUseCase @Inject constructor(
    private val localMatchesRepository: LocalMatchesRepository
) {
    fun execute(list : List<FootballMatch>){
        localMatchesRepository.saveMatches(list)
    }
}