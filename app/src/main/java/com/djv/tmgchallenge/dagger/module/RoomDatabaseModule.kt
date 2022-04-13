package com.djv.tmgchallenge.dagger.module

import android.app.Application
import androidx.room.Room
import com.djv.tmgchallenge.data.TmgDatabase
import com.djv.tmgchallenge.data.dao.GameDao
import com.djv.tmgchallenge.data.dao.PlayerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDatabaseModule(application: Application) {

    private var libraryApplication = application
    private lateinit var libraryDatabase: TmgDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(): TmgDatabase {
        libraryDatabase = Room.databaseBuilder(libraryApplication, TmgDatabase::class.java, "tmg-database")
            .fallbackToDestructiveMigration()
            .build()
        return libraryDatabase
    }

    //@Singleton
    @Provides
    fun providesPlayerDao(libraryDatabase: TmgDatabase): PlayerDao = libraryDatabase.playerDao()

    //@Singleton
    @Provides
    fun providesGameDao(libraryDatabase: TmgDatabase): GameDao = libraryDatabase.gameDao()
}