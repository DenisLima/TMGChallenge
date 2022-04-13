package com.djv.tmgchallenge.domain

import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import io.reactivex.Completable
import io.reactivex.Single

class GameUseCasesImpl (
    private val gameRepository: GameRepository
): GameUseCases {

    override fun getAllGames(): Single<List<Game>> {
        return gameRepository.getAllGames()
    }

    override fun getAllPlayers(): Single<List<Player>> {
        return gameRepository.getAllPlayers()
    }

    override fun initPlayers(): Completable {
        return gameRepository.initPlayers()
    }

    override fun deletePlayer(player: Player): Completable {
        gameRepository.deleteGameByPlayerId(player.id)
        return gameRepository.deletePlayer(player)
    }

    override fun updatePlayer(player: Player): Completable {
        return gameRepository.updatePlayer(player)
    }

    override fun getPlayerByName(playerName: String): Single<Player> {
        return gameRepository.getPlayerByName(playerName)
    }

    override fun insertPlayer(player: Player): Completable {
        return gameRepository.insertPlayer(player)
    }

    override fun initGames(): Completable {
        return gameRepository.initGames()
    }

    override fun getPlayerAndGame(): Single<List<PlayerAndGame>> {
        return gameRepository.getPlayerAndGame()
    }

    override fun insertGames(game: Game): Completable {
        return gameRepository.insertGame(game)
    }

    override fun deleteGameByid(gameId: Int): Completable {
        return gameRepository.deleteGameByid(gameId)
    }

    override fun getCountMatch(playerId: Int): Single<Int> {
        return gameRepository.getCountMatch(playerId)
    }

    override fun getWinsMatch(playerId: Int): Single<Int> {
        return gameRepository.getWinsMatch(playerId)
    }

    override fun getRanking(player: Player): Single<Ranking> {
        return gameRepository.getRanking(player)
    }

    override fun deleteGameByPlayerId(playerId: Int): Completable {
        return gameRepository.deleteGameByPlayerId(playerId)
    }
}