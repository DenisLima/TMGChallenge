package com.djv.tmgchallenge.domain

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getAllGames(): Single<List<Game>>
    fun getAllPlayers(): Single<List<Player>>
    suspend fun initPlayers()
    fun deletePlayer(player: Player): Completable
    fun updatePlayer(player: Player): Completable
    fun getPlayerByName(playerName: String): Single<Player>
    fun insertPlayer(player: Player): Completable
    suspend fun initGames()
    fun getPlayerAndGame(): Single<List<PlayerAndGame>>
    fun insertGame(game: Game): Completable
    fun deleteGameByid(gameId: Int): Completable
    fun getCountMatch(playerId: Int): Single<Int>
    fun getWinsMatch(playerId: Int): Single<Int>
    fun getSecondWinsMatch(playerId: Int): Single<Int>
    fun getRanking(player: Player): Single<Ranking>
    fun deleteGameByPlayerId(playerId: Int): Completable
}