package com.example.premierleaguefixtures.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.premierleaguefixtures.domain.models.FootballMatch

@Entity(tableName = MatchDao.MATCH_TABLE)
data class MatchEntity(
    @PrimaryKey
    val matchNumber: Int,
    val roundNumber: Int,
    val dateUtc: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val group: String?,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
)

fun MatchEntity.toFootballMatch() : FootballMatch {
    return FootballMatch(
        matchNumber = matchNumber,
        roundNumber = roundNumber,
        dateUtc = dateUtc,
        location = location,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        group = group,
        homeTeamScore = homeTeamScore,
        awayTeamScore = awayTeamScore
    )
}

fun FootballMatch.toMatchEntity() : MatchEntity {
    return MatchEntity(
        matchNumber = matchNumber,
        roundNumber = roundNumber,
        dateUtc = dateUtc,
        location = location,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        group = group,
        homeTeamScore = homeTeamScore,
        awayTeamScore = awayTeamScore
    )
}
