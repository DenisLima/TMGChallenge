package com.djv.tmgchallenge.data.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

const val APP_DB_NAME = "appTmgDb"

inline fun <reified T : RoomDatabase> createRoomDb(context: Context, dbName: String): T {
    return Room
        .databaseBuilder(context.applicationContext, T::class.java, dbName)
        .fallbackToDestructiveMigration()
        .build()
}