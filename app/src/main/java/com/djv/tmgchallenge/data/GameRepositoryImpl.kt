package com.djv.tmgchallenge.data

import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.data.model.Ranking
import com.djv.tmgchallenge.domain.GameRepository
import io.reactivex.Completable
import io.reactivex.Single

class GameRepositoryImpl(
    private val gameDataSource: GameDataSource
) : GameRepository {

    override fun getAllGames(): Single<List<Game>> {
        return gameDataSource.getAllGame()
    }

    override fun getAllPlayers(): Single<List<Player>> {
        return gameDataSource.getAllPlayer()
    }

    override suspend fun initPlayers() {
        gameDataSource.initPlayers()
    }

    override fun deletePlayer(player: Player): Completable {
        return gameDataSource.deletePlayer(player)
    }

    override fun updatePlayer(player: Player): Completable {
        return gameDataSource.updatePlayer(player)
    }

    override fun getPlayerByName(playerName: String): Single<Player> {
        return gameDataSource.getPlayerByName(playerName)
    }

    override fun insertPlayer(player: Player): Completable {
        return gameDataSource.insertPlayer(player)
    }

    override suspend fun initGames() {
        gameDataSource.initGames()
    }

    override fun getPlayerAndGame(): Single<List<PlayerAndGame>> {
        return gameDataSource.getPlayerAndGame()
    }

    override fun insertGame(game: Game): Completable {
        return gameDataSource.insertGame(game)
    }

    override fun deleteGameByid(gameId: Int): Completable {
        return gameDataSource.deleteGameById(gameId)
    }

    override fun getCountMatch(playerId: Int): Single<Int> {
        return gameDataSource.getCountMatch(playerId)
    }

    override fun getWinsMatch(playerId: Int): Single<Int> {
        return gameDataSource.getMainWinsMatch(playerId)
    }

    override fun getSecondWinsMatch(playerId: Int): Single<Int> {
        return gameDataSource.getSecondWinsMatch(playerId)
    }

    override fun deleteGameByPlayerId(playerId: Int): Completable {
        return gameDataSource.deleteGameByPlayerId(playerId)
    }

    override fun getRanking(player: Player): Single<Ranking> {
        val counter = getCountMatch(player.id)
        val wins = getWinsMatch(player.id)
        val secondWins = getSecondWinsMatch(player.id)
        val playerName = getPlayerByName(player.name)
        return Single.zip<Int, Int, Player, Int, Ranking>(
            counter,
            wins,
            playerName,
            secondWins
        ) { counter, wins, playerName, secondWins ->
            Ranking(
                playerName = playerName.name,
                mainCount = counter,
                visitorCount = secondWins,
                wins = wins
            )
        }

    }
}