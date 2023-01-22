package com.example.premierleaguefixtures.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Upsert
    fun upsertAll(users: List<MatchEntity>)

    @Query("SELECT * FROM $MATCH_TABLE WHERE homeTeam LIKE '%' || :teamName ||'%' or awayTeam LIKE '%' || :teamName ||'%'")
    fun getMatchesByTeamName(teamName:String): Flow<List<MatchEntity>>

    @Query("SELECT * FROM $MATCH_TABLE WHERE matchNumber = :number")
    fun getMatchesByNumber(number:Int): Flow<MatchEntity>

    companion object{
        const val MATCH_TABLE = "match_table"
    }
}