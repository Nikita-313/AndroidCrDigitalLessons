package com.example.premierleaguefixtures.domain.models


data class FootballMatch(
    val matchNumber: Int,
    val roundNumber: Int,
    val dateUtc: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val group: String?,
    val homeTeamScore: Int = 0,
    val awayTeamScore: Int = 0
)
