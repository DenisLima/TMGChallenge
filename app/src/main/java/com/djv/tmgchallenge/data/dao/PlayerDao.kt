package com.djv.tmgchallenge.data.dao

import androidx.room.*
import com.djv.tmgchallenge.data.model.Player

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    suspend fun getAllPlayers(): List<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(player: List<Player>)

    @Delete
    suspend fun delete(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * FROM player WHERE playerName = :name")
    suspend fun getPlayerByName(name: String) : Player
}