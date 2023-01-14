package com.example.premierleaguefixtures.di

import android.content.Context
import com.example.premierleaguefixtures.data.MatchDatabase
import com.example.premierleaguefixtures.data.local.MatchesLocalRepository
import com.example.premierleaguefixtures.data.network.MatchesNetworkRepository
import com.example.premierleaguefixtures.data.network.MatchesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fixturedownload.com/feed/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMatchDB(): MatchDatabase? {
        return MatchDatabase.getDatabase();
    }

    @Provides
    fun provideNetworkService(
        retrofit: Retrofit
    ): MatchesService {
        return retrofit.create(MatchesService::class.java)
    }

    @Provides
    fun provideMatchesNetworkRepository(
        matchesService: MatchesService,
        database: MatchDatabase?,
        @ApplicationContext context: Context
    ): MatchesNetworkRepository {
        return MatchesNetworkRepository(
            matchesService = matchesService,
            database = database,
            context = context
        )
    }

    @Provides
    fun provideMatchesLocalRepository(database: MatchDatabase?): MatchesLocalRepository {
        return MatchesLocalRepository(database = database)
    }
}