package com.djv.tmgchallenge.data.di

import com.djv.tmgchallenge.data.GameDataSourceImpl
import com.djv.tmgchallenge.data.GameRepositoryImpl
import com.djv.tmgchallenge.data.TmgDatabase
import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.data.utils.APP_DB_NAME
import com.djv.tmgchallenge.data.utils.createRoomDb
import com.djv.tmgchallenge.domain.GameRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<GameDataSource> {
        GameDataSourceImpl(get())
    }

    single<GameRepository> {
        GameRepositoryImpl(get())
    }

    single(named(APP_DB_NAME)) {
        "tmg-application-db"
    }

    single {
        createRoomDb<TmgDatabase>(get(), get(named(APP_DB_NAME)))
    }

    single {
        get<TmgDatabase>().playerDao()
    }

    single {
        get<TmgDatabase>().gameDao()
    }
}