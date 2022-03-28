package com.djv.tmgchallenge.data.datasource

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame

interface GameDataSource {

    suspend fun getAllGame(): List<Game>
    suspend fun getAllPlayer(): List<Player>
    suspend fun initPlayers()
    suspend fun deletePlayer(player: Player)
    suspend fun updatePlayer(player: Player)
    suspend fun getPlayerByName(playerName: String): Player
    suspend fun insertPlayer(player: Player)
    suspend fun initGames()
    suspend fun getPlayerAndGame(): List<PlayerAndGame>
    suspend fun insertGame(game: Game)
    suspend fun deleteGameById(gameId: Int)
    suspend fun getCountMatch(playerId: Int): Int
    suspend fun getMainWinsMatch(playerId: Int): Int
    suspend fun getSecondWinsMatch(playerId: Int): Int
    suspend fun deleteGameByPlayerId(playerId: Int)
}