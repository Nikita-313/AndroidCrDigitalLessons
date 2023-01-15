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

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun  provideLoadMatchesUseCase(networkMatchesRepository: NetworkMatchesRepository,saveMatchesUseCase: SaveMatchesUseCase,@ApplicationContext context: Context) : LoadMatchesUseCase {
        return LoadMatchesUseCase(networkMatchesRepository = networkMatchesRepository, saveMatchesUseCase = saveMatchesUseCase, context = context)
    }

    @Provides
    fun  provideSaveMatchesUseCase(localMatchesRepository: LocalMatchesRepository): SaveMatchesUseCase {
        return SaveMatchesUseCase(localMatchesRepository = localMatchesRepository)
    }

    @Provides
    fun  provideGetSavedMatchesUseCase(localMatchesRepository: LocalMatchesRepository) : GetSavedMatchesUseCase {
        return GetSavedMatchesUseCase(localMatchesRepository = localMatchesRepository)
    }

    @Provides
    fun  provideSearchMatchesByTeamNameUseCase(localMatchesRepository: LocalMatchesRepository) : SearchMatchesByTeamNameUseCase {
        return SearchMatchesByTeamNameUseCase(localMatchesRepository = localMatchesRepository)
    }

    @Provides
    fun  provideGetMatchByNumberUseCase(localMatchesRepository: LocalMatchesRepository) : GetMatchByNumberUseCase {
        return GetMatchByNumberUseCase(localMatchesRepository = localMatchesRepository)
    }
}