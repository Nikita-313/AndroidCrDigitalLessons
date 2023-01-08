package com.example.premierleaguefixtures.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM $MATCH_TABLE")
    fun getAll(): Flow<List<MatchEntity>>

    @Insert
    fun insertAll(users: List<MatchEntity>)

    @Query("DELETE FROM $MATCH_TABLE")
    fun clearTable()

    @Query("SELECT * FROM $MATCH_TABLE WHERE homeTeam LIKE '%' || :teamName ||'%' or awayTeam LIKE '%' || :teamName ||'%'")
    fun getMatchesByTeamName(teamName:String): Flow<List<MatchEntity>>

    companion object{
        const val MATCH_TABLE = "match_table"
    }
}