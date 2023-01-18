package com.example.premierleaguefixtures.data.repository

import com.example.premierleaguefixtures.data.local.MatchDatabase
import com.example.premierleaguefixtures.data.local.toFootballMatch
import com.example.premierleaguefixtures.data.local.toMatchEntity
import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMatchesRepositoryImpl @Inject constructor(
    private val database: MatchDatabase?
) : LocalMatchesRepository {

    override fun getMatchesByTeamName(teamName: String): Flow<List<FootballMatch>> {
        return database?.matchDao()?.getMatchesByTeamName(teamName)
            ?.map { list -> list.map { it.toFootballMatch() } }!!
    }

    override fun getMatchByNumber(number: Int): Flow<FootballMatch> {
        return database?.matchDao()?.getMatchesByNumber(number)
            ?.map { it.toFootballMatch() }!!
    }

    override fun saveMatches(list: List<FootballMatch>) {
        database?.matchDao()?.upsertAll(list.map { it.toMatchEntity() })
    }
}