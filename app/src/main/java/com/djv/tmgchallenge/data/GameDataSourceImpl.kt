package com.djv.tmgchallenge.data

import com.djv.tmgchallenge.data.datasource.GameDataSource
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GameDataSourceImpl (
    private val tmgDatabase: TmgDatabase
): GameDataSource {

    override fun getAllGame(): Single<List<Game>> = tmgDatabase.gameDao().getAllGames()

    override fun getAllPlayer(): Single<List<Player>> = tmgDatabase.playerDao().getAllPlayers()

    override fun initPlayers(): Completable {
        val list = mutableListOf<Player>()
        list.add(Player(1, "Amos"))
        list.add(Player(2, "Diego"))
        list.add(Player(3, "Joel"))
        list.add(Player(4, "Tim"))
        return Observable.fromIterable(list)
            .flatMapCompletable {
                tmgDatabase.playerDao().insertPlayer(it)
                    .subscribeOn(Schedulers.io())
            }
    }

    override fun deletePlayer(player: Player): Completable = tmgDatabase.playerDao().delete(player)
    override fun updatePlayer(player: Player): Completable = tmgDatabase.playerDao().update(player)
    override fun getPlayerByName(playerName: String): Single<Player> = tmgDatabase.playerDao().getPlayerByName(playerName)
    override fun insertPlayer(player: Player): Completable {
        return tmgDatabase.playerDao().insertPlayer(player)
    }

    //comment here
    override fun initGames(): Completable {
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
        return Observable.fromIterable(gameList)
            .flatMapCompletable {
                tmgDatabase.gameDao().insertGame(it)
            }
    }

    override fun getPlayerAndGame(): Single<List<PlayerAndGame>> {
        return tmgDatabase.gameDao().getGameAndPlayer()
    }

    override fun insertGame(game: Game): Completable = tmgDatabase.gameDao().insertGame(game)
    override fun deleteGameById(gameId: Int): Completable = tmgDatabase.gameDao().deleteGameByid(gameId)
    override fun getCountMatch(playerId: Int): Single<Int> = tmgDatabase.gameDao().getCountGameByPlayerId(playerId)
    override fun getMainWinsMatch(playerId: Int): Single<Int> = tmgDatabase.gameDao().getWinsMainGameByPlayerId(playerId)
    override fun getSecondWinsMatch(playerId: Int): Single<Int> = tmgDatabase.gameDao().getWinsSecondGameByPlayerId(playerId)
    override fun deleteGameByPlayerId(playerId: Int): Completable = tmgDatabase.gameDao().deleteGameByPlayerId(playerId)
}