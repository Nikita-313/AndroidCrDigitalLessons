package com.example.premierleaguefixtures.data.local

import com.example.premierleaguefixtures.data.MatchDatabase
import com.example.premierleaguefixtures.data.model.Match
import com.example.premierleaguefixtures.data.model.toMatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchesLocalRepository @Inject constructor(
    private val database: MatchDatabase?
) {

    fun getMatches(): Flow<List<Match>> = flow {
        database?.matchDao()?.getAll()?.collect { list ->
            emit(list.map { it.toMatch() })
        }
    }

    fun searchMatchesByTeamName(teamName: String): Flow<List<Match>> = flow {
        database?.matchDao()?.getMatchesByTeamName(teamName)
            ?.collect { list ->
                emit(list.map { it.toMatch() })
            }
    }

    fun getMatchByNumber(number: Int) = flow {
        database?.matchDao()?.getMatchesByNumber(number)
            ?.collect { it ->
                emit(it.toMatch())
            }
    }
}