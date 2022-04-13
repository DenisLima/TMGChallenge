package com.djv.tmgchallenge.domain

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import io.reactivex.Completable
import io.reactivex.Single

interface GameUseCases {

    fun getAllGames(): Single<List<Game>>
    fun getAllPlayers(): Single<List<Player>>
    fun initPlayers(): Completable
    fun deletePlayer(player: Player): Completable
    fun updatePlayer(player: Player): Completable
    fun getPlayerByName(playerName: String): Single<Player>
    fun insertPlayer(player: Player): Completable
    fun initGames(): Completable
    fun getPlayerAndGame(): Single<List<PlayerAndGame>>
    fun insertGames(game: Game): Completable
    fun deleteGameByid(gameId: Int): Completable
    fun getCountMatch(playerId: Int): Single<Int>
    fun getWinsMatch(playerId: Int): Single<Int>
    fun getRanking(player: Player): Single<Ranking>
    fun deleteGameByPlayerId(playerId: Int): Completable
}