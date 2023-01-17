package com.example.premierleaguefixtures.data.repository

import com.example.premierleaguefixtures.data.network.models.toFootballMatch
import com.example.premierleaguefixtures.data.network.service.MatchesService
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkMatchesRepositoryImpl @Inject constructor(
    private val matchesService: MatchesService,
) : NetworkMatchesRepository {
    override fun getMatches(): Flow<List<FootballMatch>> = flow {
        val list = matchesService.matches().map { it.toFootballMatch() }
        emit(list)
    }

}