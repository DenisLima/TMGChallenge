package com.djv.tmgchallenge.dagger.module

import com.djv.tmgchallenge.data.GameDataSourceImpl
import com.djv.tmgchallenge.data.GameRepositoryImpl
import com.djv.tmgchallenge.data.TmgDatabase
import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.domain.GameRepository
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.domain.GameUseCasesImpl
import com.djv.tmgchallenge.data.registerdata.PlayerAddData
import com.djv.tmgchallenge.data.registerdata.MatchRegisterData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GameModule {

    @Singleton
    @Provides
    fun providesGameDataSource(libraryDatabase: TmgDatabase): GameDataSource {
        return GameDataSourceImpl(libraryDatabase)
    }

    @Singleton
    @Provides
    fun providesGameRepository(gameDataSource: GameDataSource): GameRepository {
        return GameRepositoryImpl(gameDataSource)
    }

    @Provides
    fun providesGameUseCase(gameRepository: GameRepository): GameUseCases {
        return GameUseCasesImpl(gameRepository = gameRepository)
    }

    @Singleton
    @Provides
    fun providesPlayerAdd(): PlayerAddData {
        return PlayerAddData()
    }

    @Singleton
    @Provides
    fun providesMatchAdd(): MatchRegisterData {
        return MatchRegisterData()
    }
}