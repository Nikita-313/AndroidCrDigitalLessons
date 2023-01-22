package com.example.premierleaguefixtures.data.network.models

import com.example.premierleaguefixtures.domain.models.FootballMatch
import com.google.gson.annotations.SerializedName

data class FootballMatchNetwork(
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
    val group: String? = null,
    @SerializedName("HomeTeamScore")
    val homeTeamScore: Int = 0,
    @SerializedName("AwayTeamScore")
    val awayTeamScore: Int = 0
)

fun FootballMatchNetwork.toFootballMatch() : FootballMatch {
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