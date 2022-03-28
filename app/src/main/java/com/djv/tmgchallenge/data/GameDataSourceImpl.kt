package com.djv.tmgchallenge.data

import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame

class GameDataSourceImpl(
    private val tmgDatabase: TmgDatabase
): GameDataSource {

    override suspend fun getAllGame(): List<Game> = tmgDatabase.gameDao().getAllGames()

    override suspend fun getAllPlayer(): List<Player> = tmgDatabase.playerDao().getAllPlayers()

    override suspend fun initPlayers() {
        val playerList = mutableListOf<Player>()
        playerList.add(Player(1, "Amos"))
        playerList.add(Player(2, "Diego"))
        playerList.add(Player(3, "Joel"))
        playerList.add(Player(4, "Tim"))
        tmgDatabase.playerDao().insertAll(playerList)
    }

    override suspend fun deletePlayer(player: Player) = tmgDatabase.playerDao().delete(player)
    override suspend fun updatePlayer(player: Player) = tmgDatabase.playerDao().update(player)
    override suspend fun getPlayerByName(playerName: String): Player = tmgDatabase.playerDao().getPlayerByName(playerName)
    override suspend fun insertPlayer(player: Player) = tmgDatabase.playerDao().insertAll(listOf(player))

    override suspend fun initGames() {
        val gameList = mutableListOf<Game>()
        gameList.add(Game(1, 1, 4, 2, 5, "24/03/2022"))
        gameList.add(Game(2, 1, 1, 2, 5, "24/03/2022"))
        gameList.add(Game(3, 1, 2, 2, 5, "24/03/2022"))
        gameList.add(Game(4, 1, 0, 2, 5, "24/03/2022"))
        gameList.add(Game(5, 1, 6, 2, 5, "24/03/2022"))
        gameList.add(Game(6, 1, 5, 2, 2, "24/03/2022"))
        gameList.add(Game(7, 1, 4, 2, 0, "24/03/2022"))
        gameList.add(Game(8, 3, 4, 2, 5, "24/03/2022"))
        gameList.add(Game(9, 4, 4, 1, 5, "24/03/2022"))
        gameList.add(Game(10, 4, 5, 1, 2, "24/03/2022"))
        gameList.add(Game(11, 1, 3, 4, 5, "24/03/2022"))
        gameList.add(Game(12, 1, 5, 4, 3, "24/03/2022"))
        gameList.add(Game(13, 1, 5, 3, 4, "24/03/2022"))
        gameList.add(Game(14, 3, 5, 4, 2, "24/03/2022"))
        initPlayers()
        tmgDatabase.gameDao().insertAll(gameList)
    }

    override suspend fun getPlayerAndGame(): List<PlayerAndGame> {
        return tmgDatabase.gameDao().getGameAndPlayer()
    }

    override suspend fun insertGame(game: Game) = tmgDatabase.gameDao().insertAll(listOf(game))
    override suspend fun deleteGameById(gameId: Int) = tmgDatabase.gameDao().deleteGameByid(gameId)
    override suspend fun getCountMatch(playerId: Int): Int = tmgDatabase.gameDao().getCountGameByPlayerId(playerId)
    override suspend fun getMainWinsMatch(playerId: Int): Int = tmgDatabase.gameDao().getWinsMainGameByPlayerId(playerId)
    override suspend fun getSecondWinsMatch(playerId: Int): Int = tmgDatabase.gameDao().getWinsSecondGameByPlayerId(playerId)
    override suspend fun deleteGameByPlayerId(playerId: Int) = tmgDatabase.gameDao().deleteGameByPlayerId(playerId)
}