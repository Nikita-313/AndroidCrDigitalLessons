package com.example.premierleaguefixtures.data.repository

import com.example.premierleaguefixtures.data.network.MatchesService
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NetworkMatchesRepositoryImpl @Inject constructor(
    private val matchesService: MatchesService,
) : NetworkMatchesRepository {
    override fun getMatches(): Flow<Response<List<FootballMatch>>> {
        return flow {
            emit(matchesService.matches())
        }
    }
}