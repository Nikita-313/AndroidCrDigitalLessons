package com.example.premierleaguefixtures.di

import com.example.premierleaguefixtures.data.local.MatchDatabase
import com.example.premierleaguefixtures.data.network.service.MatchesService
import com.example.premierleaguefixtures.data.repository.LocalMatchesRepositoryImpl
import com.example.premierleaguefixtures.data.repository.NetworkMatchesRepositoryImpl
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideNetworkMatchesRepository(matchesService: MatchesService) : NetworkMatchesRepository {
        return NetworkMatchesRepositoryImpl(matchesService = matchesService)
    }

    @Provides
    fun provideLocalMatchesRepository(database: MatchDatabase?) : LocalMatchesRepository {
        return LocalMatchesRepositoryImpl(database)
    }

}