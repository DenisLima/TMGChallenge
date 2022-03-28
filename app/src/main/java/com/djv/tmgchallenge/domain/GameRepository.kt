package com.djv.tmgchallenge.domain

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun getAllGames(): Flow<List<Game>>
    suspend fun getAllPlayers(): Flow<List<Player>>
    suspend fun initPlayers()
    suspend fun deletePlayer(player: Player)
    suspend fun updatePlayer(player: Player)
    suspend fun getPlayerByName(playerName: String): Flow<Player>
    suspend fun insertPlayer(player: Player)
    suspend fun initGames()
    suspend fun getPlayerAndGame(): Flow<List<PlayerAndGame>>
    suspend fun insertGame(game: Game)
    suspend fun deleteGameByid(gameId: Int)
    suspend fun getCountMatch(playerId: Int): Flow<Int>
    suspend fun getWinsMatch(playerId: Int): Flow<Int>
    suspend fun getSecondWinsMatch(playerId: Int): Flow<Int>
    suspend fun getRanking(player: Player): Flow<Ranking>
    suspend fun deleteGameByPlayerId(playerId: Int)
}