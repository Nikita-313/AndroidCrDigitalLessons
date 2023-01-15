package com.example.premierleaguefixtures.domain.models

import com.google.gson.annotations.SerializedName


data class FootballMatch(
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
