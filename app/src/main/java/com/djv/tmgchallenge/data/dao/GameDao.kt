package com.djv.tmgchallenge.data.dao

import androidx.room.*
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.PlayerAndGame

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(game: List<Game>)

    @Delete
    suspend fun delete(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("SELECT A.id as idGame, B.playerName as mainName, A.mainScore as mainScore, C.playerName as secondName, A.secondScore as secondScore, A.dateRegister as dateRegister" +
            "            FROM game as A INNER JOIN player as B ON B.id = A.mainPlayer" +
            "            INNER JOIN player C ON A.secondPlayer=C.id ORDER BY A.id desc")
    suspend fun getGameAndPlayer(): List<PlayerAndGame>

    @Query("DELETE FROM game WHERE id = :gameId")
    suspend fun deleteGameByid(gameId: Int)

    @Query("select count(*) from game where mainPlayer = :playerId or secondPlayer = :playerId")
    suspend fun getCountGameByPlayerId(playerId: Int): Int

    @Query("select count(*) from game where mainPlayer = :playerId and mainScore > secondScore")
    suspend fun getWinsMainGameByPlayerId(playerId: Int): Int

    @Query("select count(*) from game where secondPlayer = :playerId and secondScore > mainScore")
    suspend fun getWinsSecondGameByPlayerId(playerId: Int): Int

    @Query("delete from game WHERE mainPlayer = :playerId or secondPlayer = :playerId")
    suspend fun deleteGameByPlayerId(playerId: Int)
}