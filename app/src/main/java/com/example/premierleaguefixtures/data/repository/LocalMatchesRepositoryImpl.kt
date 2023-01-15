package com.example.premierleaguefixtures.data.repository

import com.example.premierleaguefixtures.data.local.MatchDatabase
import com.example.premierleaguefixtures.data.local.toFootballMatch
import com.example.premierleaguefixtures.data.local.toMatchEntity
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalMatchesRepositoryImpl @Inject constructor(
    private val database: MatchDatabase?
) : LocalMatchesRepository {

    override suspend fun getMatches(): Flow<List<FootballMatch>> {
        return flow {
            database?.matchDao()?.getAll()?.collect { list ->
                emit(list.map { it.toFootballMatch() })
            }
        }
    }

    override fun searchMatchesByTeamName(teamName: String): Flow<List<FootballMatch>> {
        return flow {
            database?.matchDao()?.getMatchesByTeamName(teamName)?.collect { list ->
                emit(list.map { it.toFootballMatch() })
            }
        }
    }

    override fun getMatchByNumber(number: Int): Flow<FootballMatch> {
        return flow {
            database?.matchDao()?.getMatchesByNumber(number)?.collect { it ->
                emit(it.toFootballMatch())
            }
        }
    }

    override fun saveMatches(list: List<FootballMatch>) {
        database?.matchDao()?.clearTable()
        database?.matchDao()?.insertAll(list.map { it.toMatchEntity()})
    }
}