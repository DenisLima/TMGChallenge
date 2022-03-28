package com.djv.tmgchallenge.domain

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameUseCasesImpl(
    private val gameRepository: GameRepository
): GameUseCases {

    override suspend fun getAllGames(): Flow<List<Game>> {
        return gameRepository.getAllGames()
    }

    override suspend fun getAllPlayers(): Flow<List<Player>> {
        return gameRepository.getAllPlayers()
    }

    override suspend fun initPlayers() {
        gameRepository.initPlayers()
    }

    override suspend fun deletePlayer(player: Player) {
        gameRepository.deleteGameByPlayerId(player.id)
        gameRepository.deletePlayer(player)
    }

    override suspend fun updatePlayer(player: Player) {
        gameRepository.updatePlayer(player)
    }

    override suspend fun getPlayerByName(playerName: String): Flow<Player> {
        return gameRepository.getPlayerByName(playerName)
    }

    override suspend fun insertPlayer(player: Player) {
        gameRepository.insertPlayer(player)
    }

    override suspend fun initGames() {
        gameRepository.initGames()
    }

    override suspend fun getPlayerAndGame(): Flow<List<PlayerAndGame>> {
        return gameRepository.getPlayerAndGame()
    }

    override suspend fun insertGames(game: Game) {
        return gameRepository.insertGame(game)
    }

    override suspend fun deleteGameByid(gameId: Int) {
        gameRepository.deleteGameByid(gameId)
    }

    override suspend fun getCountMatch(playerId: Int): Flow<Int> {
        return gameRepository.getCountMatch(playerId)
    }

    override suspend fun getWinsMatch(playerId: Int): Flow<Int> {
        return gameRepository.getWinsMatch(playerId)
    }

    override suspend fun getRanking(player: Player): Flow<Ranking> {
        return gameRepository.getRanking(player)
    }

    override suspend fun deleteGameByPlayerId(playerId: Int) {
        gameRepository.deleteGameByPlayerId(playerId)
    }
}