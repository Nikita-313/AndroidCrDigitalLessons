package com.example.premierleaguefixtures.domain.repository
import com.example.premierleaguefixtures.domain.models.FootballMatch
import kotlinx.coroutines.flow.Flow


interface LocalMatchesRepository {

    fun getMatchesByTeamName(teamName: String):  Flow<List<FootballMatch>>

    fun getMatchByNumber(number: Int): Flow<FootballMatch>

    fun saveMatches(list: List<FootballMatch>)

}