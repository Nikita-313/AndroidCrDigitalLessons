package com.example.premierleaguefixtures.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MatchDao.MATCH_TABLE)
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
