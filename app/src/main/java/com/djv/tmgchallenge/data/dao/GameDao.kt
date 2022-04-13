package com.djv.tmgchallenge.data.dao

import androidx.room.*
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.PlayerAndGame
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getAllGames(): Single<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game): Completable

    @Delete
    fun delete(game: Game): Completable

    @Update
    fun update(game: Game): Completable

    @Query("SELECT A.id as idGame, B.playerName as mainName, A.mainScore as mainScore, C.playerName as secondName, A.secondScore as secondScore, A.dateRegister as dateRegister" +
            "            FROM game as A INNER JOIN player as B ON B.id = A.mainPlayer" +
            "            INNER JOIN player C ON A.secondPlayer=C.id ORDER BY A.id desc")
    fun getGameAndPlayer(): Single<List<PlayerAndGame>>

    @Query("DELETE FROM game WHERE id = :gameId")
    fun deleteGameByid(gameId: Int): Completable

    @Query("select count(*) from game where mainPlayer = :playerId or secondPlayer = :playerId")
    fun getCountGameByPlayerId(playerId: Int): Single<Int>

    @Query("select count(*) from game where mainPlayer = :playerId and mainScore > secondScore")
    fun getWinsMainGameByPlayerId(playerId: Int): Single<Int>

    @Query("select count(*) from game where secondPlayer = :playerId and secondScore > mainScore")
    fun getWinsSecondGameByPlayerId(playerId: Int): Single<Int>

    @Query("delete from game WHERE mainPlayer = :playerId or secondPlayer = :playerId")
    fun deleteGameByPlayerId(playerId: Int): Completable
}