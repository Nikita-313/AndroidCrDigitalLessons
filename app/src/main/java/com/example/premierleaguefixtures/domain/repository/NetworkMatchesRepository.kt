package com.example.premierleaguefixtures.domain.repository

import com.example.premierleaguefixtures.domain.models.FootballMatch
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NetworkMatchesRepository {
    fun getMatches(): Flow<Response<List<FootballMatch>>>
}