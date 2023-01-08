package com.example.premierleaguefixtures.data.model

import com.example.premierleaguefixtures.data.local.MatchEntity
import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("MatchNumber")
    val matchNumber: Int,
    @SerializedName("RoundNumber")
    val roundNumber: Int,
    @SerializedName("DateUtc")
    val dateUtc: String,
    @SerializedName("Location")
    val location: String,
    @SerializedName("HomeTeam")
    val homeTeam: String,
    @SerializedName("AwayTeam")
    val awayTeam: String,
    @SerializedName("Group")
    val group: String?,
    @SerializedName("HomeTeamScore")
    val homeTeamScore: Int = 0,
    @SerializedName("AwayTeamScore")
    val awayTeamScore: Int = 0
)

fun MatchEntity.toMatch() : Match {
    return Match(
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

fun Match.toMatchEntity() : MatchEntity {
    return MatchEntity(
        id = 0,
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
