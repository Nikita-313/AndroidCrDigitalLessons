package com.example.premierleaguefixtures.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MatchEntity::class], version = 1)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao

    companion object {
        private var INSTANCE: MatchDatabase? = null

        fun createDatabase(context: Context): Boolean {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MatchDatabase::class.java, "match-database"
                    ).build()
                    return true;
                }
            }
            return false;
        }

        fun getDatabase(): MatchDatabase? = INSTANCE;
    }
}