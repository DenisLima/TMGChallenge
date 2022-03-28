package com.djv.tmgchallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djv.tmgchallenge.data.dao.GameDao
import com.djv.tmgchallenge.data.dao.PlayerDao
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player

@Database(entities = [Player::class, Game::class], version = 1, exportSchema = false)
abstract class TmgDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
}