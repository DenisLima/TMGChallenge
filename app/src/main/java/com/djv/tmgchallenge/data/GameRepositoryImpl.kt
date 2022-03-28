package com.djv.tmgchallenge.data

import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import com.djv.tmgchallenge.domain.GameRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class GameRepositoryImpl(
    private val gameDataSource: GameDataSource
): GameRepository {

    override suspend fun getAllGames(): Flow<List<Game>> {
        return flow {
            delay(3000)
            val itemList = gameDataSource.getAllGame()
            this.emit(itemList)
        }
    }

    override suspend fun getAllPlayers(): Flow<List<Player>> {
        return flow {
            delay(3000)
            val itemList = gameDataSource.getAllPlayer()
            this.emit(itemList)
        }
    }

    override suspend fun initPlayers() {
        gameDataSource.initPlayers()
    }

    override suspend fun deletePlayer(player: Player) {
        gameDataSource.deletePlayer(player)
    }

    override suspend fun updatePlayer(player: Player) {
        gameDataSource.updatePlayer(player)
    }

    override suspend fun getPlayerByName(playerName: String): Flow<Player> {
        return flow {
            val item = gameDataSource.getPlayerByName(playerName)
            this.emit(item)
        }
    }

    override suspend fun insertPlayer(player: Player) {
        gameDataSource.insertPlayer(player)
    }

    override suspend fun initGames() {
        gameDataSource.initGames()
    }

    override suspend fun getPlayerAndGame(): Flow<List<PlayerAndGame>> {
        return flow {
            delay(3000)
            val item = gameDataSource.getPlayerAndGame()
            this.emit(item)
        }
    }

    override suspend fun insertGame(game: Game) {
        gameDataSource.insertGame(game)
    }

    override suspend fun deleteGameByid(gameId: Int) {
        gameDataSource.deleteGameById(gameId)
    }

    override suspend fun getCountMatch(playerId: Int): Flow<Int> {
        return flow {
            val item = gameDataSource.getCountMatch(playerId)
            this.emit(item)
        }
    }

    override suspend fun getWinsMatch(playerId: Int): Flow<Int> {
        return flow {
            val item = gameDataSource.getMainWinsMatch(playerId)
            this.emit(item)
        }
    }

    override suspend fun getSecondWinsMatch(playerId: Int): Flow<Int> {
        return flow {
            val item = gameDataSource.getSecondWinsMatch(playerId)
            this.emit(item)
        }
    }

    override suspend fun deleteGameByPlayerId(playerId: Int) {
        gameDataSource.deleteGameByPlayerId(playerId)
    }

    override suspend fun getRanking(player: Player): Flow<Ranking> {
        val counter = getCountMatch(player.id)
        val wins = getWinsMatch(player.id)
        val secondWins = getSecondWinsMatch(player.id)
        val playerName = getPlayerByName(player.name)
        return combine(counter, wins, playerName, secondWins) { counter, wins, playerName, secondWins ->
            Ranking(
                playerName = playerName.name,
                mainCount = counter,
                visitorCount = secondWins,
                wins = wins
            )
        }
    }
}