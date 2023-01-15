package com.example.premierleaguefixtures.domain.repository
import com.example.premierleaguefixtures.domain.models.FootballMatch
import kotlinx.coroutines.flow.Flow


interface LocalMatchesRepository {

    suspend fun getMatches(): Flow<List<FootballMatch>>

    fun searchMatchesByTeamName(teamName: String):  Flow<List<FootballMatch>>

    fun getMatchByNumber(number: Int): Flow<FootballMatch>

    fun saveMatches(list: List<FootballMatch>)

}