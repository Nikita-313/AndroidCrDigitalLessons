package com.example.premierleaguefixtures.di

import android.content.Context
import androidx.room.Room
import com.example.premierleaguefixtures.data.local.MatchDatabase
import com.example.premierleaguefixtures.data.network.service.MatchesService
import com.example.premierleaguefixtures.data.repository.LocalMatchesRepositoryImpl
import com.example.premierleaguefixtures.data.repository.NetworkMatchesRepositoryImpl
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fixturedownload.com/feed/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        retrofit: Retrofit
    ): MatchesService {
        return retrofit.create(MatchesService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkMatchesRepository(matchesService: MatchesService) : NetworkMatchesRepository {
        return NetworkMatchesRepositoryImpl(matchesService = matchesService)
    }

    @Provides
    @Singleton
    fun provideLocalMatchesRepository(database: MatchDatabase) : LocalMatchesRepository {
        return LocalMatchesRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MatchDatabase {
        return Room.databaseBuilder(
            appContext,
            MatchDatabase::class.java,
            "match-database"
        ).build()
    }

}