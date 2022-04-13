package com.djv.tmgchallenge.ui.matches

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.domain.GameUseCases
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MatchViewModel @Inject constructor(
    private val gameUseCases: GameUseCases
) : ViewModel() {

    //LiveData Region
    private val fetchGamesLv = MutableLiveData<List<PlayerAndGame>>()
    fun getFetchGames(): LiveData<List<PlayerAndGame>> = fetchGamesLv

    private val isFetchDataLv = MutableLiveData<Unit>()
    fun getFetchData(): LiveData<Unit> = isFetchDataLv

    private val playerListLv = MutableLiveData<List<Player>>()
    fun getPlayers(): LiveData<List<Player>> = playerListLv

    private val notifyAdapterLv = MutableLiveData<Unit>()
    fun getNotifyAdapter(): LiveData<Unit> = notifyAdapterLv

    private val isSamePlayerLv = MutableLiveData<Unit>()
    fun getSamePlayer(): LiveData<Unit> = isSamePlayerLv

    private val _isInsertMatch = MutableLiveData<Unit>()
    fun isInsertMatch(): LiveData<Unit> = _isInsertMatch
    //LiveData Region end

    //Call region
    @SuppressLint("CheckResult")
    fun fetchGames() {
        gameUseCases.getPlayerAndGame()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.isEmpty()) {
                        insertInitGames()
                    } else {
                        fetchGamesLv.postValue(it)
                    }
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun insertInitGames() {
        gameUseCases.initGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    isFetchDataLv.postValue(Unit)
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    @SuppressLint("CheckResult")
    fun getPlayerList() {
        gameUseCases.getAllPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    playerListLv.postValue(it)
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    @SuppressLint("CheckResult")
    fun insertGame(game: Game) {
        if (game.mainPlayer == game.secondPlayer) {
            isSamePlayerLv.postValue(Unit)
        } else {
            gameUseCases.insertGames(game)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                       _isInsertMatch.postValue(Unit)
                    },
                    onError = {
                        Log.e("ERROR", it.message.toString())
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun deleteGame(game: PlayerAndGame) {
        gameUseCases.deleteGameByid(game.idGame)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    notifyAdapterLv.postValue(Unit)
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }
    //Call region end
}