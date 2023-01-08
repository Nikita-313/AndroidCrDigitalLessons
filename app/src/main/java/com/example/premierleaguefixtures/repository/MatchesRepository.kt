package com.example.premierleaguefixtures.repository

import com.example.premierleaguefixtures.data.model.Match
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MatchesRepository {

    private val footballMatches: Array<Match> = Array<Match>(20) {
        Match(
            matchNumber = it,
            roundNumber = it % 5,
            dateUtc = "2021-08-13 19:00:00Z",
            location = "Brentford Community Stadium",
            homeTeam = "Brentford",
            awayTeam = "Arsenal",
            group = null,
            homeTeamScore = it,
            awayTeamScore = it+1,
        )
    }

    fun getFootballMatches() : Flow<List<Match>> = flow{
        emit(footballMatches.toList())
    }
}