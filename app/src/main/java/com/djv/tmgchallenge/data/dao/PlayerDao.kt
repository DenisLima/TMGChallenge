package com.djv.tmgchallenge.data.dao

import androidx.room.*
import com.djv.tmgchallenge.data.model.Player
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    fun getAllPlayers(): Single<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(player: List<Player>) : Completable

    @Delete
    fun delete(player: Player) : Completable

    @Update
    fun update(player: Player) : Completable

    @Query("SELECT * FROM player WHERE playerName = :name")
    fun getPlayerByName(name: String) : Single<Player>
}