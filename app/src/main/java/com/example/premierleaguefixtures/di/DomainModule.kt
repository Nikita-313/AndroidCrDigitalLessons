package com.example.premierleaguefixtures.di

import android.content.Context
import com.example.premierleaguefixtures.domain.repository.LocalMatchesRepository
import com.example.premierleaguefixtures.domain.repository.NetworkMatchesRepository
import com.example.premierleaguefixtures.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    @Singleton
    fun  provideLoadMatchesUseCase(networkMatchesRepository: NetworkMatchesRepository,saveMatchesUseCase: SaveMatchesUseCase,@ApplicationContext context: Context) : LoadMatchesUseCase {
        return LoadMatchesUseCase(networkMatchesRepository = networkMatchesRepository, saveMatchesUseCase = saveMatchesUseCase, context = context)
    }

    @Provides
    @Singleton
    fun  provideSaveMatchesUseCase(localMatchesRepository: LocalMatchesRepository): SaveMatchesUseCase {
        return SaveMatchesUseCase(localMatchesRepository = localMatchesRepository)
    }

    @Provides
    @Singleton
    fun  provideSearchMatchesByTeamNameUseCase(localMatchesRepository: LocalMatchesRepository) : GetMatchesByTeamNameUseCase {
        return GetMatchesByTeamNameUseCase(localMatchesRepository = localMatchesRepository)
    }

    @Provides
    @Singleton
    fun  provideGetMatchByNumberUseCase(localMatchesRepository: LocalMatchesRepository) : GetMatchByNumberUseCase {
        return GetMatchByNumberUseCase(localMatchesRepository = localMatchesRepository)
    }
}