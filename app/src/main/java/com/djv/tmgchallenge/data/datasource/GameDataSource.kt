package com.djv.tmgchallenge.data.datasource

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import io.reactivex.Completable
import io.reactivex.Single

interface GameDataSource {

    fun getAllGame(): Single<List<Game>>
    fun getAllPlayer(): Single<List<Player>>
    suspend fun initPlayers()
    fun deletePlayer(player: Player): Completable
    fun updatePlayer(player: Player): Completable
    fun getPlayerByName(playerName: String): Single<Player>
    fun insertPlayer(player: Player): Completable
    suspend fun initGames()
    fun getPlayerAndGame(): Single<List<PlayerAndGame>>
    fun insertGame(game: Game): Completable
    fun deleteGameById(gameId: Int): Completable
    fun getCountMatch(playerId: Int): Single<Int>
    fun getMainWinsMatch(playerId: Int): Single<Int>
    fun getSecondWinsMatch(playerId: Int): Single<Int>
    fun deleteGameByPlayerId(playerId: Int): Completable
}